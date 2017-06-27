package com.vdvideos.downloader.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.vdvideos.downloader.common.entity.UpdateInfoEntity;

import java.util.concurrent.Callable;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by hanhanh.nguyen on 6/16/2017.
 */

public class NetworkModel {

    @Inject OkHttpClient okHttpClient;

    private String updateUrl = "http://tubemate.biz/api/fbvideodownloader.json";

    @Inject
    public NetworkModel(){

    }

    public Observable<UpdateInfoEntity> getUpdateInfo(){
        final Request request = new Request.Builder()
                .url(updateUrl)
                .build();

        return Observable.fromCallable(new Callable<UpdateInfoEntity>() {
            @Override
            public UpdateInfoEntity call() throws Exception {
                Gson gson = new GsonBuilder().create();
                Response response = okHttpClient.newCall(request).execute();
                UpdateInfoEntity item = gson.fromJson(response.body().charStream(), UpdateInfoEntity.class);
                return item;
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

}
