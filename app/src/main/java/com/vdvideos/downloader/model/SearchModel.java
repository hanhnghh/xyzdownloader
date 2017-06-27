package com.vdvideos.downloader.model;

import com.vdvideos.downloader.MyApplication;
import com.vdvideos.downloader.common.api.SearchApi;
import com.vdvideos.downloader.common.entity.SearchVideoEntity;

import javax.inject.Inject;

import io.reactivex.Observable;

import retrofit2.Retrofit;

/**
 * Created by hanhanh.nguyen on 6/16/2017.
 */

public class SearchModel {

    @Inject Retrofit retrofit;
    private String fieldResponse = "title,thumbnail_url,owner,views_total";

    @Inject
    public SearchModel(){

    }

    public Observable<SearchVideoEntity> searchVideos(final String query, final int page){
        return retrofit.create(SearchApi.class).searchVideos(query, page, MyApplication.VIDEO_PER_PAGE, fieldResponse);
    }
}
