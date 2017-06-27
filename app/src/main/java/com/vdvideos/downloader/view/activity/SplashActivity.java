package com.vdvideos.downloader.view.activity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.InterstitialAd;
import com.google.gson.Gson;
import com.vdvideos.downloader.R;
import com.vdvideos.downloader.common.entity.UpdateInfoEntity;
import com.vdvideos.downloader.presenter.SplashPresenter;
import com.vdvideos.downloader.view.SplashView;

import javax.inject.Inject;

/**
 * Created by hanhanh.nguyen on 6/15/2017.
 */

public class SplashActivity extends BaseActivity implements SplashView{

    @Inject SplashPresenter presenter;
    @Inject SharedPreferences sharedPreferences;

    private InterstitialAd interstitialAd;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getActivityComponent().inject(this);
        presenter.setView(this);
        getUpdateInfo();
    }

    private void getUpdateInfo(){
        presenter.getUpdateInfo();
    }

    @Override
    public void setUpdateInfo(UpdateInfoEntity entity) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(getString(R.string.interstitial_id_key), entity.interstitial_ads);
        editor.putString(getString(R.string.native_id_key), entity.native_large_ads);
        editor.putString(getString(R.string.banner_id_key), entity.banner_ads);
        editor.putString(getString(R.string.intro_key), entity.guide);
        editor.putString(getString(R.string.browser_btn_key), entity.browse);
        editor.putBoolean(getString(R.string.yt_key), entity.yt);

        String countries = new Gson().toJson(entity.country);
        editor.putString(getString(R.string.country_key), countries);
        editor.apply();
    }

    @Override
    public void showUpdateDialog(final UpdateInfoEntity entity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle(entity.update_title)
                .setMessage(entity.update_message);

        if(entity.update_cancelable){
            builder.setPositiveButton(getResources().getText(R.string.action_ok), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int i) {
                    showPlayStore(entity.update_link);
                }
            })
                    .setNegativeButton(getResources().getText(R.string.action_later), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int i) {
                            dialog.dismiss();
                        }
                    });
        } else {
            builder.setPositiveButton(getResources().getText(R.string.action_ok), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    showPlayStore(entity.update_link);
                }
            });
        }

        Dialog dialog = builder.create();
        dialog.show();
    }

    public void showPlayStore(String url) {
        try {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
        } catch (android.content.ActivityNotFoundException e){
            e.printStackTrace();
        }
    }

    @Override
    public void loadInterstitialAd(final InterstitialAd interstitialAd) {
        this.interstitialAd = interstitialAd;
        interstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                super.onAdClosed();
                showSearchActivity();
            }

            @Override
            public void onAdFailedToLoad(int i) {
                super.onAdFailedToLoad(i);
                showSearchActivity();
            }

            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                interstitialAd.show();
            }
        });
    }

    private void showSearchActivity(){
        startActivity(new Intent(this, SearchActivity.class));
        finish();
    }
}
