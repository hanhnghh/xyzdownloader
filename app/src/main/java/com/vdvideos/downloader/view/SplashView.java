package com.vdvideos.downloader.view;

import com.google.android.gms.ads.InterstitialAd;
import com.vdvideos.downloader.common.entity.UpdateInfoEntity;

/**
 * Created by hanhanh.nguyen on 6/15/2017.
 */

public interface SplashView {

    void setUpdateInfo(UpdateInfoEntity entity);

    void showUpdateDialog(UpdateInfoEntity entity);

    void loadInterstitialAd(InterstitialAd interstitialAd);

    void showSearchActivity();
}
