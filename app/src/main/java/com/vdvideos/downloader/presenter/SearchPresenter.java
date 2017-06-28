package com.vdvideos.downloader.presenter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.google.android.gms.ads.InterstitialAd;
import com.vdvideos.downloader.common.entity.SearchVideoEntity;
import com.vdvideos.downloader.common.entity.VideoEntity;
import com.vdvideos.downloader.model.AdsModel;
import com.vdvideos.downloader.model.ImageModel;
import com.vdvideos.downloader.model.SearchModel;
import com.vdvideos.downloader.view.SearchView;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by hanhanh.nguyen on 6/16/2017.
 */

public class SearchPresenter {

    private SearchView searchView;
    private Context context;
    private Boolean hasMore;
    private Boolean isLoading;

    @Inject SearchModel searchModel;
    @Inject ImageModel imageModel;
    @Inject AdsModel adsModel;

    @Inject
    public SearchPresenter(Context context){
        this.context = context;
    }

    public void setView(SearchView view){
        searchView = view;
    }

    public void setCanLoadMore(boolean hasMore){
        this.hasMore = hasMore;
    }

    public void setLoading(boolean loading){
        isLoading = loading;
    }

    public boolean canLoadMore(){
        return !isLoading && hasMore;
    }

    public void searchVideo(String text, int page){
        requestPhotos(text, page, true);
        searchView.showLoadingView();
        setLoading(true);
    }

    public void loadMore(String text, int page){
        requestPhotos(text, page, false);
        setLoading(true);
    }

    public void requestPhotos(String query, int page, final boolean isFirst){
        final int newPage = isFirst ? 1 : page + 1;
        searchModel.searchVideos(query, newPage)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<SearchVideoEntity>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                    }

                    @Override
                    public void onNext(@NonNull SearchVideoEntity searchVideoEntityList) {
                        if(isFirst) {
                            searchView.hideLoadingView();
                            searchView.clearList();
                        }
                        setLoading(false);
                        setCanLoadMore(searchVideoEntityList.has_more);
                        searchView.setVideoPage(newPage);
                        for(VideoEntity entity : searchVideoEntityList.list){
                            searchView.insertItem(entity);
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    public void loadThumbnailImage(ImageView view, String url){
           imageModel.loadThumbnailImage(view, url);
    }

    public void loadInterstitialAds(String adsId){
        OnRequestAdsListener listener = new OnRequestAdsListener();
        adsModel.loadInterstitialAds(adsId, listener);
    }

    private class OnRequestAdsListener implements AdsModel.OnRequestAdsListener{
        @Override
        public void onRequestAdsSuccessful(InterstitialAd interstitialAd) {
            searchView.setInterstitialAd(interstitialAd);
        }
    }
}
