package com.vdvideos.downloader.di.component;

import com.vdvideos.downloader.di.PerActivity;
import com.vdvideos.downloader.di.module.ActivityModule;
import com.vdvideos.downloader.view.activity.SearchActivity;
import com.vdvideos.downloader.view.activity.SplashActivity;

import dagger.Component;

/**
 * Created by hanhanh.nguyen on 6/16/2017.
 */
@PerActivity
@Component(dependencies = AppComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {
    void inject(SplashActivity splashActivity);
    void inject(SearchActivity searchActivity);
}
