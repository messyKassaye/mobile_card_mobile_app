package com.example.foragentss.auth.retailers.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.dantsu.escposprinter.EscPosPrinter;
import com.dantsu.escposprinter.connection.bluetooth.BluetoothPrintersConnections;
import com.dantsu.escposprinter.exceptions.EscPosBarcodeException;
import com.dantsu.escposprinter.exceptions.EscPosConnectionException;
import com.dantsu.escposprinter.exceptions.EscPosEncodingException;
import com.dantsu.escposprinter.exceptions.EscPosParserException;
import com.dantsu.escposprinter.textparser.PrinterTextParserImg;
import com.example.foragentss.R;
import com.example.foragentss.constants.Constants;
import com.example.foragentss.rooms.view_model.CardsRoomViewModel;

public class PrinterActivity extends AppCompatActivity {

    private EscPosPrinter printer;
    TextView printInfo,errorShower;
    Button printBtn;
    EditText amountText;
    private CardsRoomViewModel cardsRoomViewModel;
    private int card_type_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_printer);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Print cards");
        card_type_id = Integer.parseInt(getIntent().getStringExtra("card_type_id"));

        cardsRoomViewModel = ViewModelProviders.of(this).get(CardsRoomViewModel.class);


        printInfo = findViewById(R.id.printer_info);
        printInfo.setText("You are going to print "+ Constants.getCardTypeValue(card_type_id)+" Birr card");

        amountText = findViewById(R.id.amount);
        errorShower = findViewById(R.id.errorShower);

        printBtn = findViewById(R.id.printBTN);
        printBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String amount = amountText.getText().toString();
                if (amount.equals("")){
                    errorShower.setText("Please enter the amount of card you want to print");
                }else {
                    startPrinting(Integer.parseInt(amount));
                }
            }
        });

    }

    public void startPrinting(int amount){
        cardsRoomViewModel.printCard(card_type_id,"not_soled",amount)
                .observe(this,cardRooms -> {
                    try {
                        printer =new EscPosPrinter(BluetoothPrintersConnections.selectFirstPaired(),203,48f,23);

                        for(int i=0;i<cardRooms.size();i++){
                            try {
                                printer.printFormattedText(
                                        "[L]<img>" + PrinterTextParserImg.bitmapToHexadecimalString(printer, this.getApplicationContext().getResources().getDrawableForDensity(R.drawable.ethio_tele, DisplayMetrics.DENSITY_MEDIUM))+"</img>\n"+
                                        "[R]<img>" + PrinterTextParserImg.bitmapToHexadecimalString(printer, this.getApplicationContext().getResources().getDrawableForDensity(R.drawable.company, DisplayMetrics.DENSITY_MEDIUM))+"</img>\n"
                                );
                            } catch (EscPosParserException e) {
                                Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
                            } catch (EscPosEncodingException e) {
                                Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
                            } catch (EscPosBarcodeException e) {
                                Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
                            }
                        }
                    } catch (EscPosConnectionException e) {
                        e.printStackTrace();
                        Toast.makeText(this,e.getMessage(),Toast.LENGTH_LONG).show();
                    }
                });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }
}
