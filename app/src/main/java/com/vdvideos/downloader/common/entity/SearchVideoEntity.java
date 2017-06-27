package com.vdvideos.downloader.common.entity;

import java.util.List;

/**
 * Created by hanhanh.nguyen on 6/16/2017.
 */

public class SearchVideoEntity {

    public int page;
    public int limit;
    public boolean explicit;
    public int total;
    public boolean has_more;

    public List<VideoEntity> videoList;
}
