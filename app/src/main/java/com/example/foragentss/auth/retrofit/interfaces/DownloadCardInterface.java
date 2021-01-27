package com.example.foragentss.auth.retrofit.interfaces;

import com.example.foragentss.auth.models.Card;
import com.example.foragentss.auth.models.Download;
import com.example.foragentss.auth.models.DownloadData;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface DownloadCardInterface {

    @GET("retailers/download_card/{card_type_id}/{amount}")
    Call<DownloadData> show(@Path("card_type_id")int card_type_id,@Path("amount")int amoun);

    @POST("download_card")
    Call<ArrayList<Card>> download(@Body Download download);
}
