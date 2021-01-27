package com.example.foragentss.rooms.respository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.foragentss.rooms.ApplicationRoomDatabase;
import com.example.foragentss.rooms.DAO.DownloadCardDAO;
import com.example.foragentss.rooms.entity.DownloadCardRoom;

import java.util.List;

public class DownloadCardRoomRepository {
    private DownloadCardDAO downloadCardDAO;

    public DownloadCardRoomRepository(Application application){
        ApplicationRoomDatabase applicationRoomDatabase = ApplicationRoomDatabase.getDatabase(application);

        downloadCardDAO = applicationRoomDatabase.downloadCardDAO();
    }

    public LiveData<List<DownloadCardRoom>> index(){
        return downloadCardDAO.index();
    }

    public void store(DownloadCardRoom downloadCardRoom){
        ApplicationRoomDatabase.dbExecutorService.execute(()->{
            downloadCardDAO.store(downloadCardRoom);
        });
    }
}
