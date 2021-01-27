package com.example.foragentss.rooms.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.foragentss.rooms.entity.DownloadCardRoom;

import java.util.List;

@Dao
public interface DownloadCardDAO {

    @Query("select * from downloads")
    public LiveData<List<DownloadCardRoom>> index();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void store(DownloadCardRoom downloadCardRoom);
}
