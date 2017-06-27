package com.vdvideos.downloader.view.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.vdvideos.downloader.MyApplication;
import com.vdvideos.downloader.di.component.ActivityComponent;
import com.vdvideos.downloader.di.component.AppComponent;
import com.vdvideos.downloader.di.component.DaggerActivityComponent;
import com.vdvideos.downloader.di.module.ActivityModule;

/**
 * Created by hanhanh.nguyen on 6/16/2017.
 */

public class BaseActivity extends AppCompatActivity {

    private ActivityComponent activityComponent;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        initActivityComponent();
    }

    protected AppComponent getAppComponent(){
        return ((MyApplication)getApplication()).getAppComponent();
    }

    private void initActivityComponent(){
        activityComponent = DaggerActivityComponent.builder()
                .appComponent(getAppComponent())
                .activityModule(new ActivityModule(this))
                .build();
    }

    public ActivityComponent getActivityComponent(){
        return activityComponent;
    }
}
