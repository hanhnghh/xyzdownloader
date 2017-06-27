package com.vdvideos.downloader.model;

import android.content.Context;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import javax.inject.Inject;

/**
 * Created by hanhanh.nguyen on 6/16/2017.
 */

public class AdsModel {

    private InterstitialAd mInterstitialAd;
    private AdRequest adRequest;
    private Context context;

    @Inject
    public AdsModel(Context context){
        this.context = context;
    }

    public void loadInterstitialAds(String adsId, OnRequestAdsListener listener){
        mInterstitialAd = new InterstitialAd(context);
        mInterstitialAd.setAdUnitId(adsId);
        adRequest = new AdRequest.Builder()
                .addTestDevice("YOUR_DEVICE_HASH")
                .build();

        mInterstitialAd.loadAd(adRequest);
        listener.onRequestAdsSuccessful(mInterstitialAd);
    }

    public interface OnRequestAdsListener {
        void onRequestAdsSuccessful(InterstitialAd interstitialAd);
    }
}