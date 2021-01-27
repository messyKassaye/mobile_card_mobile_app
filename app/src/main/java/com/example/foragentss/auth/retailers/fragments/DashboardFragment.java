package com.example.foragentss.auth.retailers.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.foragentss.R;
import com.example.foragentss.auth.models.CardType;
import com.example.foragentss.auth.retailers.adapter.CardsAdapter;
import com.example.foragentss.auth.retailers.adapter.MyCardsAdapter;
import com.example.foragentss.auth.view_model.CardTypeViewModel;
import com.example.foragentss.constants.Constants;
import com.example.foragentss.rooms.entity.CardTypeRoom;
import com.example.foragentss.rooms.view_model.CardTypeRoomViewModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DashboardFragment extends Fragment {
    private RecyclerView recyclerView;
    private CardTypeRoomViewModel cardTypeRoomViewModel;
    private ArrayList<CardTypeRoom> cardTypeRoomArrayList =new ArrayList<>();
    private MyCardsAdapter adapter;
    public DashboardFragment() {
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
        View view= inflater.inflate(R.layout.dashboard_fragment, container, false);

        adapter = new MyCardsAdapter(getActivity(),cardTypeRoomArrayList);
        recyclerView = view.findViewById(R.id.cardTypeRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        cardTypeRoomViewModel = ViewModelProviders.of(getActivity()).get(CardTypeRoomViewModel.class);
        cardTypeRoomViewModel.index().observe(getActivity(),cardTypeRooms -> {
            if (cardTypeRooms.size()>0){
                if (cardTypeRoomArrayList.size()<=0){
                    cardTypeRoomArrayList.addAll(cardTypeRooms);
                }
                adapter.notifyDataSetChanged();
            }
        });
        return view;
    }

}
