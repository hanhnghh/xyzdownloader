package com.vdvideos.downloader.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.vdvideos.downloader.MyApplication;
import com.vdvideos.downloader.R;
import com.vdvideos.downloader.common.entity.VideoEntity;
import com.vdvideos.downloader.view.SearchView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hanhanh.nguyen on 6/17/2017.
 */

public class VideoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    @Inject Context context;
    private List<VideoEntity> videoList;
    private int videoPages;
    private OnItemClickListener onItemClickListener;
    private SearchView searchView;

    @Inject
    public VideoAdapter(){
        videoList = new ArrayList<>();
        videoPages = getRealItemCount() / MyApplication.VIDEO_PER_PAGE;
    }

    public void setView(SearchView view){
        searchView = view;
    }

    public void setVideoList(List<VideoEntity> list){
        videoList.clear();
        videoList.addAll(list);
        notifyDataSetChanged();
    }

    public void insertVideoEntity(VideoEntity item){
        videoList.add(item);
        notifyDataSetChanged();
    }

    public void clearList(){
        videoList.clear();
        notifyDataSetChanged();
    }

    public int getRealItemCount(){
        return videoList.size();
    }

    public int getPageCount(){
        return videoPages;
    }

    public void setVideoPage(int page){
        videoPages = page;
    }

    public void setOnVideoEntityClicked(OnItemClickListener listener){
        onItemClickListener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.video_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof ViewHolder){
            final VideoEntity entity = videoList.get(position);
            ((ViewHolder)holder).onBindView(position);
            ((ViewHolder) holder).videoContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(onItemClickListener != null) {
                        onItemClickListener.onVideoItemClicked(entity);
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return (videoList != null) ? videoList.size() : 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.video_container) LinearLayout videoContainer;
        @BindView(R.id.thumbnail_image) ImageView thumbnailImage;
        @BindView(R.id.video_title) TextView videoTitle;
        @BindView(R.id.video_owner) TextView videoOwner;
        @BindView(R.id.video_views_count) TextView videoViewCount;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void onBindView(int position){
            searchView.loadThumbnailImage(thumbnailImage, videoList.get(position).thumbnail_url);
            videoTitle.setText(videoList.get(position).title);
            videoOwner.setText(videoList.get(position).owner);
            videoViewCount.setText(videoList.get(position).view_total);
        }
    }

    public interface OnItemClickListener{
        void onVideoItemClicked(VideoEntity item);
    }
}
