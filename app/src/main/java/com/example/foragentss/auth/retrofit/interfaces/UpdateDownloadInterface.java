package com.example.foragentss.auth.retrofit.interfaces;

import com.example.foragentss.auth.models.DownloadCardData;
import com.example.foragentss.auth.response.SuccessResponse;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UpdateDownloadInterface {

    @POST("update_download_print")
    Observable<SuccessResponse> store(@Body DownloadCardData downloadCardData);
}
