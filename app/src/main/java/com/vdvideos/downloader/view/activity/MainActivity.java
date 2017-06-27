package com.vdvideos.downloader.view.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.vdvideos.downloader.R;
import com.vdvideos.downloader.presenter.MainPresenter;

import javax.inject.Inject;

public class MainActivity extends BaseActivity {

    @Inject MainPresenter presenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getActivityComponent().inject(this);
    }
}
