package com.vdvideos.downloader.presenter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.vdvideos.downloader.common.entity.SearchVideoEntity;
import com.vdvideos.downloader.common.entity.VideoEntity;
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

    @Inject SearchModel searchModel;
    @Inject ImageModel imageModel;

    @Inject
    public SearchPresenter(Context context){
        this.context = context;
    }

    public void setView(SearchView view){
        searchView = view;
    }

    public void searchVideo(String text, int page){
        requestPhotos(text, page, true);
        searchView.showLoadingView();
    }

    public boolean canLoadMore(){
        return true;
    }

    public void loadMore(String text, int page){
        requestPhotos(text, page, false);
    }

    public void requestPhotos(String query, int page, boolean isFirst){
        final int newPage = isFirst ? 1 : page + 1;
        searchModel.searchVideos(query, newPage)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<SearchVideoEntity>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        Log.d("han.hanh", "SearchPresenter: onSubscribe");
                    }

                    @Override
                    public void onNext(@NonNull SearchVideoEntity searchVideoEntityList) {
                        Log.d("han.hanh", "SearchPresenter: onNext");
                        searchView.hideLoadingView();
                        searchView.setVideoPage(newPage);
                        for(VideoEntity entity : searchVideoEntityList.videoList){
                            searchView.insertItem(entity);
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.d("han.hanh", "SearchPresenter: onError");
                        Log.d("han.hanh", " " + e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        Log.d("han.hanh", "SearchPresenter: onComplete");
                    }
                });
    }

    public void loadThumbnailImage(ImageView view, String url){
           imageModel.loadThumbnailImage(view, url);
    }
}
