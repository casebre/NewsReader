package com.casebre.newsreader.components;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.casebre.newsreader.feed.FeedRepository;
import com.casebre.newsreader.feed.FeedViewModel;

import javax.inject.Inject;

public class ViewModelFactory implements ViewModelProvider.Factory {

    private FeedRepository feedRepo;

    @Inject
    public ViewModelFactory(FeedRepository feedRepository) {
        this.feedRepo = feedRepository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        FeedViewModel feedViewModel = null;
        if(modelClass.isAssignableFrom(FeedViewModel.class))
            feedViewModel = new FeedViewModel(feedRepo);

        if (feedViewModel == null) {
            throw new IllegalArgumentException("unknown model class " + modelClass);
        }
        try {
            return (T) feedViewModel;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
