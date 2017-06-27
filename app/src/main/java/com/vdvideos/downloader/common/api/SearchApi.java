package com.vdvideos.downloader.common.api;

import com.vdvideos.downloader.common.entity.SearchVideoEntity;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by hanhanh.nguyen on 6/16/2017.
 */

public interface SearchApi {

    @GET("videos")
    Observable<SearchVideoEntity> searchVideos(@Query("search") String query,
                                               @Query("page") int page,
                                               @Query("limit") int per_page,
                                               @Query("fields") String fieldResponse);
}
