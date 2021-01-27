package com.example.foragentss.home.fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foragentss.MainActivity;
import com.example.foragentss.R;
import com.example.foragentss.auth.agents.AgentsDashboard;
import com.example.foragentss.auth.response.SuccessResponse;
import com.example.foragentss.auth.retailers.RetailersDashboard;
import com.example.foragentss.auth.utils.ApiResponse;
import com.example.foragentss.auth.view_model.DeviceViewModel;
import com.example.foragentss.auth.view_model.MeViewModel;
import com.example.foragentss.home.RegistrationActivity;
import com.example.foragentss.rooms.entity.CardTypeRoom;
import com.example.foragentss.rooms.entity.UserRoom;
import com.example.foragentss.rooms.view_model.CardTypeRoomViewModel;
import com.example.foragentss.rooms.view_model.UserViewModel;
import com.example.foragentss.security.CardEncryptor;
import com.example.foragentss.security.KeyCastor;

import java.util.ArrayList;


public class AgreementsFragment extends Fragment implements View.OnClickListener {
   private int roleId;
   private TextView addressInfo;
   private String infoMessage;
   private String from;
   Button yesBTN,noBTN;
   LinearLayout mainLayout,loadinglayout;
   private MeViewModel meViewModel;
   private DeviceViewModel deviceViewModel;
   private UserViewModel userViewModel;
   private String password;
   private CardTypeRoomViewModel cardTypeRoomViewModel;
    public AgreementsFragment() {
        // Required empty public constructor
    }

    public AgreementsFragment(int roleId, String message, String from,String password){
        this.roleId = roleId;
        this.infoMessage = message;
        this.from = from;
        this.password = password;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_address, container, false);

        cardTypeRoomViewModel = ViewModelProviders.of(getActivity()).get(CardTypeRoomViewModel.class);

        meViewModel = ViewModelProviders.of(getActivity()).get(MeViewModel.class);
        deviceViewModel =ViewModelProviders.of(getActivity()).get(DeviceViewModel.class);
        userViewModel =ViewModelProviders.of(getActivity()).get(UserViewModel.class);

        addressInfo = view.findViewById(R.id.addressInfo);
        addressInfo.setText(this.infoMessage);

        mainLayout = view.findViewById(R.id.addressMainLayout);
        loadinglayout = view.findViewById(R.id.addressLoadingLayout);

        yesBTN = view.findViewById(R.id.yes);
        yesBTN.setOnClickListener(this::onClick);

        noBTN = view.findViewById(R.id.no);
        noBTN.setOnClickListener(this::onClick);

        return  view;
    }




    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.yes:
                agreed();
                break;
            case R.id.no:
                break;
        }
    }

    public void agreed(){
        mainLayout.setVisibility(View.GONE);
        loadinglayout.setVisibility(View.VISIBLE);

        meViewModel.me().observe(getActivity(),meResponse -> {
            if (meResponse!=null){
                //store users locally for offline purpose
                //after users stored locally they will login at any time
                //without the need of internet. these they can print their cards
                //offline
                MainActivity mainActivity = new MainActivity();
                UserRoom userRoom = new UserRoom();
                userRoom.setPhone(meResponse.getData().getAttribute().getPhone());
                userRoom.setEmail(meResponse.getData().getAttribute().getEmail());
                userRoom.setFull_name(meResponse.getData().getAttribute().getFirst_name());
                userRoom.setId(meResponse.getData().getAttribute().getId());
                userRoom.setPassword(CardEncryptor.crypto(KeyCastor.convertIntoSecretKey(mainActivity.getNativeKey()),password,false));
                userViewModel.store(userRoom);

                //store card types for offline purpose
                storeCardType();

                Intent intent = new Intent(getActivity(), RetailersDashboard.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                getActivity().startActivity(intent);

            }
        });
    }

    public void storeCardType(){
        //if there is no previous storage of card type just store new card types
        cardTypeRoomViewModel.index().observe(getActivity(),cardTypeRooms -> {
            if (cardTypeRooms.size()<=0){
                for (int i=1;i<=6;i++){
                    CardTypeRoom cardTypeRoom=new CardTypeRoom();
                    int value = findCardValue(i);
                    cardTypeRoom.setValue(value);
                    cardTypeRoom.setId(i);
                    cardTypeRoom.setName(value+" Birr card");
                    cardTypeRoomViewModel.store(cardTypeRoom);
                }
            }
        });
    }


    public int findCardValue(int id){
        int result=0;
        switch (id){
            case 1:
                result= 5;
                break;
            case 2:
                result= 10;
                break;
            case 3:
                result= 15;
                break;
            case 4:
                result= 25;
                break;
            case 5:
                result= 50;
                break;
            case 6:
                result= 100;
                break;
        }

        return result;
    }


}
