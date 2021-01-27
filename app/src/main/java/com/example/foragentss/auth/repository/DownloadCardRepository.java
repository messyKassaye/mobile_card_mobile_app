package com.example.foragentss.auth.repository;

import com.example.foragentss.auth.models.Card;
import com.example.foragentss.auth.models.Download;
import com.example.foragentss.auth.models.DownloadData;
import com.example.foragentss.auth.retrofit.RetrofitRequest;
import com.example.foragentss.auth.retrofit.interfaces.DownloadCardInterface;

import java.util.ArrayList;

import retrofit2.Call;

public class DownloadCardRepository {
    private DownloadCardInterface downloadCardInterface;

    public DownloadCardRepository(){

        downloadCardInterface = RetrofitRequest.getApiInstance().create(DownloadCardInterface.class);
    }

    public Call<DownloadData> show(int card_type_id,int amount){
        return  downloadCardInterface.show(card_type_id,amount);
    }

    public Call<ArrayList<Card>> download(Download download){
        return downloadCardInterface.download(download);
    }
}

