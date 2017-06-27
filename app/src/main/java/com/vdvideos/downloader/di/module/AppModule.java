package com.vdvideos.downloader.di.module;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.vdvideos.downloader.MyApplication;
import com.vdvideos.downloader.model.AdsModel;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by hanhanh.nguyen on 6/15/2017.
 */
@Module
public class AppModule {

    private final MyApplication application;

    public AppModule(MyApplication application) {
        this.application = application;
    }

    @Singleton
    @Provides
    public Context provideApplicationContext(){
        return application;
    }

    @Provides
    @Singleton
    public OkHttpClient provideOkHttpClient() {
        final OkHttpClient.Builder builder = new OkHttpClient.Builder();

        builder.connectTimeout(60 * 1000, TimeUnit.MILLISECONDS)
                .readTimeout(60 * 1000, TimeUnit.MILLISECONDS);

        return builder.build();
    }

    @Provides
    @Singleton
    public Retrofit provideRetrofitBuilder(OkHttpClient okHttpClient) {
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(MyApplication.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create());

        Retrofit retrofit = builder.client(okHttpClient).build();
        return retrofit;
    }

    @Provides
    @Singleton
    public SharedPreferences provideSharedPreferences(){
        return PreferenceManager.getDefaultSharedPreferences(application);
    }

    @Provides
    @Singleton
    public AdsModel provideAdsModel(){
        return new AdsModel(application);
    }
}
