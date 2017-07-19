package com.vdvideos.downloader.presenter;

import android.util.Log;

import com.google.android.gms.ads.InterstitialAd;
import com.vdvideos.downloader.common.entity.UpdateInfoEntity;
import com.vdvideos.downloader.model.AdsModel;
import com.vdvideos.downloader.model.NetworkModel;
import com.vdvideos.downloader.view.SplashView;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

/**
 * Created by hanhanh.nguyen on 6/16/2017.
 */

public class SplashPresenter {

    private SplashView splashView;
    @Inject NetworkModel networkModel;
    @Inject AdsModel adsModel;

    @Inject
    public SplashPresenter(){

    }

    public void setView(SplashView view){
        splashView = view;
    }

    public void getUpdateInfo(){
        networkModel.getUpdateInfo().subscribe(new Observer<UpdateInfoEntity>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull UpdateInfoEntity updateInfoEntity) {
                splashView.setUpdateInfo(updateInfoEntity);
                if(updateInfoEntity.update == true) {
                    splashView.showUpdateDialog(updateInfoEntity);
                }
                loadInterstitialAds(updateInfoEntity.interstitial_ads);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                splashView.showSearchActivity();
            }

            @Override
            public void onComplete() {

            }
        });
    }

    private void loadInterstitialAds(String adsId){
        OnRequestAdsListener listener = new OnRequestAdsListener();
        adsModel.loadInterstitialAds(adsId, listener);
    }

    private class OnRequestAdsListener implements AdsModel.OnRequestAdsListener {
        @Override
        public void onRequestAdsSuccessful(InterstitialAd interstitialAd) {
            splashView.loadInterstitialAd(interstitialAd);
        }
    }
}
