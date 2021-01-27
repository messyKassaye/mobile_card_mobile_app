package com.example.foragentss.rooms.respository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.foragentss.rooms.ApplicationRoomDatabase;
import com.example.foragentss.rooms.DAO.PrintingDAO;
import com.example.foragentss.rooms.entity.DownloadCardRoom;
import com.example.foragentss.rooms.entity.Print;

import java.util.List;

public class PrintRepository {

    private PrintingDAO printingDAO;
    public PrintRepository(Application application){
        ApplicationRoomDatabase applicationRoomDatabase = ApplicationRoomDatabase.getDatabase(application);

        printingDAO = applicationRoomDatabase.printingDAO();
    }

    public LiveData<List<Print>> index(){
        return printingDAO.index();
    }

    public void store(Print downloadCardRoom){
        ApplicationRoomDatabase.dbExecutorService.execute(()->{
            printingDAO.store(downloadCardRoom);
        });
    }
}
