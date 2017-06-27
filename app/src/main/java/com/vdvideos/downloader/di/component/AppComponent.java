package com.vdvideos.downloader.di.component;

import android.content.Context;
import android.content.SharedPreferences;

import com.vdvideos.downloader.di.module.AppModule;

import javax.inject.Singleton;

import dagger.Component;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

/**
 * Created by hanhanh.nguyen on 6/15/2017.
 */
@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {

    //Exposed to sub-graphs
    Context context();
    OkHttpClient okHttpClient();
    Retrofit retrofit();
    SharedPreferences sharedPreferences();
}
