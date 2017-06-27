package com.vdvideos.downloader.di.module;

import android.app.Activity;

import com.vdvideos.downloader.di.PerActivity;

import dagger.Module;
import dagger.Provides;

/**
 * Created by hanhanh.nguyen on 6/16/2017.
 */
@Module
public class ActivityModule {
    private final Activity activity;

    public ActivityModule(Activity activity){
        this.activity = activity;
    }

    @Provides
    @PerActivity
    public Activity provideActiivty(){
        return  this.activity;
    }
}
