package com.casebre.newsreader.webservice;

import com.casebre.newsreader.NewsFeed;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Webservice {
    @GET("ctvnews-ca-top-stories-public-rss-1.822009")
    Call<NewsFeed> getNewsFeed();
}
