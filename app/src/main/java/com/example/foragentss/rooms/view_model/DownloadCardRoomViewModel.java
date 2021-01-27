package com.example.foragentss.rooms.view_model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.foragentss.rooms.entity.DownloadCardRoom;
import com.example.foragentss.rooms.respository.DownloadCardRoomRepository;

import java.util.List;

public class DownloadCardRoomViewModel extends AndroidViewModel {
    private DownloadCardRoomRepository downloadCardRoomRepository;
    public DownloadCardRoomViewModel(@NonNull Application application) {
        super(application);

        downloadCardRoomRepository = new DownloadCardRoomRepository(application);
    }

    public LiveData<List<DownloadCardRoom>> index(){
        return downloadCardRoomRepository.index();
    }

    public void store(DownloadCardRoom downloadCardRoom){
        downloadCardRoomRepository.store(downloadCardRoom);
    }
}
