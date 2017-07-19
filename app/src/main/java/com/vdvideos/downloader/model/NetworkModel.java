package com.vdvideos.downloader.model;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.vdvideos.downloader.common.entity.UpdateInfoEntity;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
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

    private String updateUrl = "https://mymotivationwebblog.files.wordpress.com/2017/07/video_config.doc";

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
                InputStream in = response.body().byteStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                String result, line = reader.readLine();
                result = line;
                while((line = reader.readLine()) != null){
                    result += line;
                }
                UpdateInfoEntity entity = gson.fromJson(result, UpdateInfoEntity.class);
                return entity;
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

}
