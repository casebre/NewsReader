package com.casebre.newsreader.components;

import com.casebre.newsreader.feed.FeedFragment;
import com.casebre.newsreader.feed.FeedRepository;

import dagger.Component;

@Component
public interface AppComponent {

    FeedRepository getFeedRepository();

    void inject(FeedFragment fragment);
}
