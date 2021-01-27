package com.example.foragentss.rooms.view_model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.foragentss.rooms.entity.DownloadCardRoom;
import com.example.foragentss.rooms.entity.Print;
import com.example.foragentss.rooms.respository.DownloadCardRoomRepository;
import com.example.foragentss.rooms.respository.PrintRepository;

import java.util.List;

public class PrintVIewModel extends AndroidViewModel {
    private PrintRepository printRepository;
    public PrintVIewModel(@NonNull Application application) {
        super(application);

        printRepository = new PrintRepository(application);
    }

    public LiveData<List<Print>> index(){
        return printRepository.index();
    }

    public void store(Print downloadCardRoom){
        printRepository.store(downloadCardRoom);
    }
}
