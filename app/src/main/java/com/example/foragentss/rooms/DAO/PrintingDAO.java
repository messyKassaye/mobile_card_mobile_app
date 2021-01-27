package com.example.foragentss.rooms.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.foragentss.rooms.entity.DownloadCardRoom;
import com.example.foragentss.rooms.entity.Print;

import java.util.List;

@Dao
public interface PrintingDAO {
    @Query("select * from prints")
    public LiveData<List<Print>> index();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void store(Print downloadCardRoom);
}
