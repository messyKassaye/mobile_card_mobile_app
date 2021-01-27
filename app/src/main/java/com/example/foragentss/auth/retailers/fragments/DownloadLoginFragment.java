package com.example.foragentss.auth.retailers.fragments;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foragentss.MainActivity;
import com.example.foragentss.R;
import com.example.foragentss.auth.models.Card;
import com.example.foragentss.auth.models.Device;
import com.example.foragentss.auth.models.Download;
import com.example.foragentss.auth.models.DownloadCardData;
import com.example.foragentss.auth.models.LoginResponse;
import com.example.foragentss.auth.response.SuccessResponse;
import com.example.foragentss.auth.retailers.activities.DownloadActivity;
import com.example.foragentss.auth.retailers.activities.RetailerActivity;
import com.example.foragentss.auth.retrofit.interfaces.DownloadCardInterface;
import com.example.foragentss.auth.utils.ApiResponse;
import com.example.foragentss.auth.view_model.DownloadCardViewModel;
import com.example.foragentss.auth.view_model.MeViewModel;
import com.example.foragentss.auth.view_model.UpdateDownloadCardViewModel;
import com.example.foragentss.constants.Constants;
import com.example.foragentss.http.MainHttpAdapter;
import com.example.foragentss.http.interfaces.LoginService;
import com.example.foragentss.rooms.entity.CardRoom;
import com.example.foragentss.rooms.entity.DownloadCardRoom;
import com.example.foragentss.rooms.view_model.CardsRoomViewModel;
import com.example.foragentss.rooms.view_model.DownloadCardRoomViewModel;
import com.example.foragentss.rooms.view_model.UserViewModel;
import com.example.foragentss.security.CardEncryptor;
import com.example.foragentss.security.KeyCastor;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class DownloadLoginFragment extends Fragment {

    private LinearLayout loadingLayout,downloadMainlayout,downloadProgress;
    private EditText amountText;
    private Button downloadBtn;
    private String tag;
    private TextView errorShower;
    private UserViewModel userViewModel;
    private int card_type_id;
    private TextView loadingText;
    private DownloadCardViewModel downloadCardViewModel;
    private CardsRoomViewModel cardsRoomViewModel;
    private StringBuilder downloadCardIds = new StringBuilder();
    private UpdateDownloadCardViewModel updateDownloadCardViewModel;

    private DownloadCardRoomViewModel downloadCardRoomViewModel;
    public DownloadLoginFragment() {
        // Required empty public constructor
    }

    public DownloadLoginFragment(String tag,int card_type_id){
        this.tag = tag;
        this.card_type_id = card_type_id;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_download_login, container, false);

        //card downloader
        downloadCardViewModel = ViewModelProviders.of(getActivity()).get(DownloadCardViewModel.class);

        cardsRoomViewModel =ViewModelProviders.of(getActivity()).get(CardsRoomViewModel.class);

        //update each cards data after download end
        updateDownloadCardViewModel = ViewModelProviders.of(getActivity()).get(UpdateDownloadCardViewModel.class);
        updateDownloadCardViewModel.storeResponse().observe(getActivity(),this::consumeResponse);

        //download card save view model
        downloadCardRoomViewModel = ViewModelProviders.of(getActivity()).get(DownloadCardRoomViewModel.class);

        //finding user information to login to server
        userViewModel = ViewModelProviders.of(getActivity()).get(UserViewModel.class);
        userViewModel.index().observe(getActivity(),userRooms -> {
            MainActivity mainActivity =new MainActivity();
            String password = CardEncryptor.crypto(KeyCastor.convertIntoSecretKey(mainActivity.getNativeKey()),userRooms.get(0).getPassword(),true);
            login(userRooms.get(0).getPhone(),password,view);
        });
        amountText = view.findViewById(R.id.amount);
        downloadBtn = view.findViewById(R.id.downloadBTN);
        errorShower = view.findViewById(R.id.errorShower);
        loadingLayout = view.findViewById(R.id.loadingLayout);
        downloadMainlayout = view.findViewById(R.id.downloadMainLayout);
        loadingText = view.findViewById(R.id.loadingText);

        downloadProgress = view.findViewById(R.id.downloadProgress);

        downloadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String amount = amountText.getText().toString();
                if (amount.equals("")){
                    errorShower.setText("Please enter the amount of card you want to download");
                }else {
                    downloadProgress.setVisibility(View.VISIBLE);
                    downloadBtn.setVisibility(View.GONE);

                    Download download=new Download();
                    download.setCard_type_id(card_type_id);
                    download.setAmount(Integer.parseInt(amount));
                    startDownloading(download);
                }
            }
        });

        return view;
    }


    public void login(String email,String password,View view){
        Retrofit retrofit= MainHttpAdapter.getAuthApi();
        LoginService loginService = retrofit.create(LoginService.class);

        Call<LoginResponse> call = loginService.login(email,password);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.code()==403){

                }else if(response.code()==200){
                    setToken(response.body().getToken());
                    downloadMainlayout.setVisibility(View.VISIBLE);
                    loadingLayout.setVisibility(View.GONE);
                    ((TextView)view.findViewById(R.id.cardType))
                            .setText("You are going to download "+Constants.getCardTypeValue(card_type_id)+" Birr card");

                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {

            }
        });
    }


    public void startDownloading(Download download){
        downloadCardViewModel.download(download)
                .enqueue(new Callback<ArrayList<Card>>() {
                    @Override
                    public void onResponse(Call<ArrayList<Card>> call, Response<ArrayList<Card>> response) {
                        if (response.body().size()>0){
                            for (int i=0;i<response.body().size();i++){
                                CardRoom cardRoom=new CardRoom();
                                cardRoom.setCard_type_id(card_type_id);
                                cardRoom.setNumber(response.body().get(i).getCard_number());
                                cardRoom.setStatus("not_soled");
                                downloadCardIds.append(response.body().get(i).getId()+",");
                                cardsRoomViewModel.store(cardRoom);
                            }
                            //send back the downloaded data ids
                            DownloadCardData data = new DownloadCardData();
                            data.setData(downloadCardIds.toString());
                            updateDownloadCardViewModel.store(data);

                            //save download data to offline
                            DownloadCardRoom downloadCardRoom=new DownloadCardRoom();
                            downloadCardRoom.setAmount(download.getAmount());
                            downloadCardRoom.setCard_type(""+Constants.getCardTypeValue(download.getCard_type_id())+" Birr");
                            downloadCardRoom.setDownload_date(Constants.currentDate());
                            downloadCardRoomViewModel.store(downloadCardRoom);


                        }else {
                            downloadProgress.setVisibility(View.GONE);
                            downloadBtn.setVisibility(View.VISIBLE);

                            errorShower.setText("There is no enough "+Constants.getCardTypeValue(card_type_id)+" Birr cards by your name.Please minimize the amount of downloads and try again):");
                        }
                    }

                    @Override
                    public void onFailure(Call<ArrayList<Card>> call, Throwable t) {

                    }
                });
    }



    private void consumeResponse(ApiResponse apiResponse) {

        switch (apiResponse.status) {

            case LOADING:
                break;

            case SUCCESS:
                renderSuccessResponse(apiResponse.data);
                break;

            case ERROR:
                System.out.println("ERRORR: "+apiResponse.error.getMessage());
                break;

            default:
                break;
        }
    }

    /*
     * method to handle success response
     * */
    private void renderSuccessResponse(SuccessResponse response) {
        System.out.println("STATUS: "+response.isStatus());
        if(response.isStatus()) {
            downloadMainlayout.setVisibility(View.GONE);
            loadingLayout.setVisibility(View.VISIBLE);
            ((ProgressBar)getView().findViewById(R.id.loadingProgress)).setVisibility(View.GONE);
            ((TextView)getView().findViewById(R.id.loadingText)).setText("Download completed successfully");
        }

    }

    public void setToken(String token){
        SharedPreferences preferences = getActivity().getSharedPreferences(Constants.getTokenPrefence(),0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("token",token);
        editor.commit();
    }

}
