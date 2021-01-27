package com.example.foragentss.auth.view_model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.foragentss.auth.models.DownloadCardData;
import com.example.foragentss.auth.repository.UpdateDownloadCardRepository;
import com.example.foragentss.auth.utils.ApiResponse;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class UpdateDownloadCardViewModel extends AndroidViewModel {
    private UpdateDownloadCardRepository downloadCardRepository;
    private final CompositeDisposable disposables = new CompositeDisposable();
    private final MutableLiveData<ApiResponse> responseLiveData = new MutableLiveData<>();

    public UpdateDownloadCardViewModel(@NonNull Application application) {
        super(application);

        downloadCardRepository = new UpdateDownloadCardRepository();
    }

    public MutableLiveData<ApiResponse> storeResponse() {
        return responseLiveData;
    }


    public void store(DownloadCardData downloadCardData){
       Observable.just(downloadCardRepository.store(downloadCardData)
               .subscribeOn(Schedulers.io())
               .observeOn(AndroidSchedulers.mainThread())
               .doOnSubscribe((d) -> responseLiveData.setValue(ApiResponse.loading()))
               .subscribe(
                       result -> responseLiveData.setValue(ApiResponse.success(result)),
                       throwable -> responseLiveData.setValue(ApiResponse.error(throwable))
               ));
   }
}
