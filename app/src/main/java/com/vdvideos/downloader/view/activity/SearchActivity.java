package com.vdvideos.downloader.view.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.google.android.gms.ads.InterstitialAd;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.vdvideos.downloader.MyApplication;
import com.vdvideos.downloader.R;
import com.vdvideos.downloader.common.entity.VideoEntity;
import com.vdvideos.downloader.presenter.SearchPresenter;
import com.vdvideos.downloader.view.SearchView;
import com.vdvideos.downloader.view.adapter.VideoAdapter;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by hanhanh.nguyen on 6/16/2017.
 */

public class SearchActivity extends BaseActivity implements SearchView{

    @Inject SearchPresenter searchPresenter;
    @Inject SharedPreferences sharedPreferences;
    @Inject VideoAdapter adapter;
    @BindView(R.id.search_box) MaterialEditText searchBox;
    @BindView(R.id.search_btn) ImageButton searchBtn;
    @BindView(R.id.videos_recyclerView) RecyclerView videoRecylerView;
    private String queryText;
    private InterstitialAd mInterstitialAd;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        getActivityComponent().inject(this);
        ButterKnife.bind(this);
        searchPresenter.setView(this);
        initView();
    }

    @Override
    public void onStart(){
        super.onStart();
        loadInterstitialAds(sharedPreferences.getString(getString(R.string.interstitial_id_key), ""));
    }

    private void initView(){
        videoRecylerView.setAdapter(adapter);
        adapter.setOnVideoEntityClicked(new VideoAdapter.OnItemClickListener() {
            @Override
            public void onVideoItemClicked(VideoEntity item) {
                showDownloadDialog();
            }
        });
        adapter.setView(this);
        videoRecylerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        videoRecylerView.addOnScrollListener(scrollListener);
    }

    @OnClick(R.id.search_btn)
    public void searchVideo(){
        String text = searchBox.getText().toString();
        if(!text.equals("")) {
            queryText = text;
            searchPresenter.searchVideo(queryText, adapter.getPageCount());
            hideKeyboard();
        }

        if(mInterstitialAd != null && mInterstitialAd.isLoaded())
            mInterstitialAd.show();
        loadInterstitialAds(sharedPreferences.getString(getString(R.string.interstitial_id_key), ""));
    }

    @Override
    public void showLoadingView(){

    }

    @Override
    public void hideLoadingView(){

    }

    @Override
    public void setVideoPage(int page) {
        adapter.setVideoPage(page);
    }

    @Override
    public void insertItem(VideoEntity entity) {
        adapter.insertVideoEntity(entity);
    }

    @Override
    public void clearList() {
        adapter.clearList();
    }

    private void hideKeyboard() {
        InputMethodManager manager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        manager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
    }

    private RecyclerView.OnScrollListener scrollListener = new RecyclerView.OnScrollListener() {

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            int lastVisibleItem = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastVisibleItemPosition();
            int totalItemCount = recyclerView.getAdapter().getItemCount() - 1;
            if (searchPresenter.canLoadMore()
                    && lastVisibleItem >= totalItemCount - 10 && totalItemCount > 0 && dy > 0) {
                searchPresenter.loadMore(queryText, adapter.getPageCount());
            }
        }
    };

    @Override
    public void loadThumbnailImage(ImageView view, String url) {
        searchPresenter.loadThumbnailImage(view, url);
    }

    public void showDownloadDialog(){
        new MaterialDialog.Builder(this)
                .title(R.string.progress_dialog)
                .content(R.string.please_wait)
                .progress(true, 0)
                .show();
    }

    private void loadInterstitialAds(String adsId){
        searchPresenter.loadInterstitialAds(adsId);
    }

    @Override
    public void setInterstitialAd(InterstitialAd interstitialAd) {
        this.mInterstitialAd = interstitialAd;
    }
}

