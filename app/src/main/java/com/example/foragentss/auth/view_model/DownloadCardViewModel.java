package com.example.foragentss.auth.view_model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.foragentss.auth.models.Card;
import com.example.foragentss.auth.models.Download;
import com.example.foragentss.auth.models.DownloadData;
import com.example.foragentss.auth.repository.DownloadCardRepository;

import java.util.ArrayList;

import retrofit2.Call;

public class DownloadCardViewModel extends AndroidViewModel {

    private DownloadCardRepository downloadCardRepository;
    public DownloadCardViewModel(@NonNull Application application) {
        super(application);

        downloadCardRepository = new DownloadCardRepository();
    }

    public Call<DownloadData> show(int card_type_id,int amount){
        return downloadCardRepository.show(card_type_id,amount);
    }

    public Call<ArrayList<Card>> download(Download download){
        return downloadCardRepository.download(download);
    }
}
