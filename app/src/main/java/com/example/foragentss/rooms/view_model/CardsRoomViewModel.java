package com.example.foragentss.rooms.view_model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.foragentss.rooms.entity.CardRoom;
import com.example.foragentss.rooms.respository.CardsRoomRepository;

import java.util.List;

public class CardsRoomViewModel extends AndroidViewModel {

    private CardsRoomRepository cardsRoomRepository;

    public CardsRoomViewModel(@NonNull Application application) {
        super(application);
        cardsRoomRepository = new CardsRoomRepository(application);
    }

    public LiveData<List<CardRoom>> index(){
        return cardsRoomRepository.index();
    }

    public LiveData<List<CardRoom>> update(int id){
        return cardsRoomRepository.update(id);
    }

    public void store(CardRoom cardRoom){
        cardsRoomRepository.store(cardRoom);
    }
    public LiveData<List<CardRoom>> showSell(String date, int type){
        return cardsRoomRepository.showSell(date,type);
    }

    public LiveData<List<CardRoom>> showCard(int cardTypeId){
        return cardsRoomRepository.show(cardTypeId);
    }

    public LiveData<List<CardRoom>> printCard(int cardType,String status,int amount){
        return  cardsRoomRepository.printCard(cardType,status,amount);
    }
}
