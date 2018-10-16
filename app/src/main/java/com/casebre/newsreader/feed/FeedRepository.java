package com.casebre.newsreader.feed;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.casebre.newsreader.NewsFeed;
import com.casebre.newsreader.webservice.Webservice;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

public class FeedRepository {
    private Webservice webservice;
    private Retrofit retrofit;

    @Inject
    public FeedRepository() {
    }

    public LiveData<NewsFeed> getFeed() {
        final MutableLiveData<NewsFeed> data = new MutableLiveData<>();
        retrofit = new Retrofit.Builder()
                .baseUrl("https://www.ctvnews.ca/rss/")
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .build();
        webservice = retrofit.create(Webservice.class);
        webservice.getNewsFeed().enqueue(new Callback<NewsFeed>() {
            @Override
            public void onResponse(Call<NewsFeed> call, Response<NewsFeed> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<NewsFeed> call, Throwable t) {
            }
        });
        return data;
    }
}
