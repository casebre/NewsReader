package com.casebre.newsreader.feed;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.casebre.newsreader.NewsFeed;

import javax.inject.Inject;

public class FeedViewModel extends ViewModel {

    private LiveData<NewsFeed> feed;
    private FeedRepository feedRepo;

    @Inject
    public FeedViewModel(FeedRepository feedRepository) {
        this.feedRepo = feedRepository;
    }

    public void init() {
        feed = feedRepo.getFeed();
    }

    public LiveData<NewsFeed> getFeed() {
        return feed;
    }

}
