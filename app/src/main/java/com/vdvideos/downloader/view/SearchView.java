package com.vdvideos.downloader.view;

import android.widget.ImageView;

import com.vdvideos.downloader.common.entity.VideoEntity;

/**
 * Created by hanhanh.nguyen on 6/16/2017.
 */

public interface SearchView {
    void showLoadingView();
    void hideLoadingView();
    void setVideoPage(int page);
    void insertItem(VideoEntity entity);
    void loadThumbnailImage(ImageView view, String url);
    void clearList();
}
