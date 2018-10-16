package com.casebre.newsreader;

import com.casebre.newsreader.feed.FeedViewModel;

import dagger.Component;

@Component
public interface AppComponent {

    //FeedRepository getFeedRepository();

    FeedViewModel getFeedViewModel();
}
