package com.example.foragentss.rooms.respository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.foragentss.rooms.ApplicationRoomDatabase;
import com.example.foragentss.rooms.DAO.CardsDAO;
import com.example.foragentss.rooms.entity.CardRoom;

import java.util.List;

public class CardsRoomRepository {
    private CardsDAO cardsDAO;

    public CardsRoomRepository(Application application){
        ApplicationRoomDatabase applicationRoomDatabase = ApplicationRoomDatabase.getDatabase(application);

        cardsDAO = applicationRoomDatabase.cardsDAO();
    }

    public LiveData<List<CardRoom>> index(){
       return cardsDAO.index();
    }
    public LiveData<List<CardRoom>> update(int id){
        return cardsDAO.update(id);
    }

    public void store(CardRoom cardRoom){
        ApplicationRoomDatabase.dbExecutorService.execute(()->{
            cardsDAO.store(cardRoom);
        });
    }
    public LiveData<List<CardRoom>> showSell(String date, int type){
        return cardsDAO.showSell(date,type);
    }

    public LiveData<List<CardRoom>> show(int cardType){
        return cardsDAO.showCard(cardType);
    }

    public LiveData<List<CardRoom>> printCard(int cardType,String status,int amount){
        return cardsDAO.printCard(cardType,status,amount);
    }

}
