package com.vdvideos.downloader.model;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import javax.inject.Inject;

/**
 * Created by hanhanh.nguyen on 6/21/2017.
 */

public class ImageModel {

    private Context context;

    @Inject
    public ImageModel(Context context){
        this.context = context;
    }

    public void loadThumbnailImage(ImageView view, String url){
        DrawableRequestBuilder<String> builder = Glide.with(context)
                                                .load(url)
                                                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                                                .listener(new RequestListener<String, GlideDrawable>() {
                                                    @Override
                                                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                                                        return false;
                                                    }

                                                    @Override
                                                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                                                        return false;
                                                    }
                                                });
        builder.into(view);
    }
}
