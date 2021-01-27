package com.example.foragentss.auth.retailers.fragments;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.foragentss.R;
import com.example.foragentss.rooms.view_model.DownloadCardRoomViewModel;


public class DownloadedCardFragment extends Fragment {

    private DownloadCardRoomViewModel downloadCardRoomViewModel;
    private LinearLayout noDownloadLayout;
    private TableLayout tableLayout;
    public DownloadedCardFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_downloaded_card, container, false);

        noDownloadLayout =view.findViewById(R.id.noDownloadLayout);

        tableLayout =view.findViewById(R.id.tableLayout);

        downloadCardRoomViewModel = ViewModelProviders.of(getActivity()).get(DownloadCardRoomViewModel.class);
        downloadCardRoomViewModel.index().observe(getActivity(),downloadCardRooms -> {
            if (downloadCardRooms.size()>0){
                noDownloadLayout.setVisibility(View.GONE);
                tableLayout.setVisibility(View.VISIBLE);
                for (int i=0;i<downloadCardRooms.size();i++){
                    TableRow tableRow=new TableRow(getActivity());

                    TextView id =new TextView(getActivity());
                    id.setText(""+downloadCardRooms.get(i).getId());
                    id.setTextColor(Color.BLACK);
                    id.setBackgroundResource(R.drawable.cell_shape);
                    id.setPadding(5,5,5,5);
                    tableRow.addView(id);

                    TextView cardType =new TextView(getActivity());
                    cardType.setText(""+downloadCardRooms.get(i).getCard_type());
                    cardType.setTextColor(Color.BLACK);
                    cardType.setBackgroundResource(R.drawable.cell_shape);
                    cardType.setPadding(5,5,5,5);
                    tableRow.addView(cardType);

                    TextView amount =new TextView(getActivity());
                    amount.setText(""+downloadCardRooms.get(i).getAmount());
                    amount.setTextColor(Color.BLACK);
                    amount.setBackgroundResource(R.drawable.cell_shape);
                    amount.setPadding(5,5,5,5);
                    tableRow.addView(amount);

                    TextView downloadDate =new TextView(getActivity());
                    downloadDate.setText(""+downloadCardRooms.get(i).getDownload_date());
                    downloadDate.setTextColor(Color.BLACK);
                    downloadDate.setBackgroundResource(R.drawable.cell_shape);
                    downloadDate.setPadding(5,5,5,5);
                    tableRow.addView(downloadDate);

                    tableLayout.addView(tableRow);
                }

            }else {
                tableLayout.setVisibility(View.GONE);
                noDownloadLayout.setVisibility(View.VISIBLE);

            }
        });

        return view;
    }

}
