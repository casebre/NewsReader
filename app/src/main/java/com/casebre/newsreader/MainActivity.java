package com.casebre.newsreader;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import com.casebre.newsreader.feed.FeedFragment;

import butterknife.BindView;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.swiperefresh) SwipeRefreshLayout swipeLayout;
    @BindView(R.id.recyclerview_news) RecyclerView listNews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportFragmentManager()
                .beginTransaction()
                .add(android.R.id.content, FeedFragment.newInstance())
                .disallowAddToBackStack()
                .commit();
    }

}
