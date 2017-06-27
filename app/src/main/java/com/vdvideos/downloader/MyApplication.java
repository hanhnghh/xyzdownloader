package com.vdvideos.downloader;

import android.app.Application;
import android.support.v4.app.ActivityCompat;

import com.vdvideos.downloader.di.component.ActivityComponent;
import com.vdvideos.downloader.di.component.AppComponent;
import com.vdvideos.downloader.di.component.DaggerActivityComponent;
import com.vdvideos.downloader.di.component.DaggerAppComponent;
import com.vdvideos.downloader.di.module.AppModule;

/**
 * Created by hanhanh.nguyen on 6/15/2017.
 */

public class MyApplication extends Application {

    public static final String BASE_URL = "https://api.dailymotion.com/";
    public static final int VIDEO_PER_PAGE = 10;
    private AppComponent appComponent;
    @Override
    public void onCreate(){
        super.onCreate();
        initComponent();
    }

    private void initComponent(){
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }

    public AppComponent getAppComponent(){
        return appComponent;
    }

}

