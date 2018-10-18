package com.casebre.newsreader.webservice;

import com.casebre.newsreader.NewsFeed;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Webservice {
    @GET("cmlink/rss-sports")
    Call<NewsFeed> getNewsFeed();
}
