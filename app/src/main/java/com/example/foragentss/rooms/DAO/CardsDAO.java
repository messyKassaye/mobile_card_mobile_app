package com.example.foragentss.rooms.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.foragentss.rooms.entity.CardRoom;

import java.util.List;

@Dao
public interface CardsDAO {

    @Query("select * from cards")
    public LiveData<List<CardRoom>> index();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void store(CardRoom cardRoom);

    @Query("select * from cards where sellsDate=:date and card_type_id=:type and status='soled'")
    public LiveData<List<CardRoom>> showSell(String date, int type);

    @Query("select * from cards where card_type_id=:cardTypeId")
    public LiveData<List<CardRoom>> showCard(int cardTypeId);

    @Query("select * from cards where id=:id")
    public LiveData<List<CardRoom>> update(int id);


    @Query("select * from cards where card_type_id=:cardTypeId and status=:status LIMIT 1")
    public LiveData<List<CardRoom>> getCardToSell(int cardTypeId, String status);

    @Query("select * from cards where card_type_id=:cardTypeId and status=:status LIMIT :amount")
    public LiveData<List<CardRoom>> printCard(int cardTypeId,String status,int amount);
}
