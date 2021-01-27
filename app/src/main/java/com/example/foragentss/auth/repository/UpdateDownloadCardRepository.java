package com.example.foragentss.auth.repository;

import com.example.foragentss.auth.models.DownloadCard;
import com.example.foragentss.auth.models.DownloadCardData;
import com.example.foragentss.auth.response.SuccessResponse;
import com.example.foragentss.auth.retrofit.RetrofitRequest;
import com.example.foragentss.auth.retrofit.interfaces.DownloadCardInterface;
import com.example.foragentss.auth.retrofit.interfaces.UpdateDownloadInterface;

import io.reactivex.Observable;

public class UpdateDownloadCardRepository {
    private UpdateDownloadInterface downloadCardInterface;

    public UpdateDownloadCardRepository(){
        downloadCardInterface = RetrofitRequest.getApiInstance().create(UpdateDownloadInterface.class);
    }

    public Observable<SuccessResponse> store(DownloadCardData downloadCard){
        return  downloadCardInterface.store(downloadCard);
    }
}
