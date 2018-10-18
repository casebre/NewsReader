package com.casebre.newsreader.feed;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.casebre.newsreader.NewsItem;
import com.casebre.newsreader.NewsRecyclerviewAdapter;
import com.casebre.newsreader.R;
import com.casebre.newsreader.components.AppComponent;
import com.casebre.newsreader.components.DaggerAppComponent;
import com.casebre.newsreader.components.ViewModelFactory;
import com.casebre.newsreader.details.NewsDetailsFragment;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FeedFragment extends Fragment implements NewsRecyclerviewAdapter.OnNewsClick {

    private static int READ_NEWS_ITEM = 100;

    private Context context;
    private FeedViewModel feedViewData;

    //private NewsItemDatabase db;
    @Inject
    ViewModelFactory viewModelFactory;

    @BindView(R.id.swiperefresh)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.recyclerview_news)
    RecyclerView listNews;

    public static FeedFragment newInstance() {
        return new FeedFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.context = getActivity();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_main,container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        swipeRefreshLayout.setOnRefreshListener(() -> swipeRefreshLayout.setRefreshing(false));

        AppComponent component = DaggerAppComponent.create();
        component.inject(this);

        feedViewData = ViewModelProviders.of(this, viewModelFactory).get(FeedViewModel.class);
        feedViewData.init();
        feedViewData.getFeed().observe(this, (newsFeed) -> loadFeedRecyclerView(newsFeed.getNewsItemList()));
    }

    protected void loadFeedRecyclerView(List<NewsItem> newsItems) {
        NewsRecyclerviewAdapter adapter = new NewsRecyclerviewAdapter(
                getActivity(),
                newsItems,
                this);
        listNews.setAdapter(adapter);
        listNews.setLayoutManager(new LinearLayoutManager(getActivity()));
        listNews.setItemAnimator(new DefaultItemAnimator());
        listNews.setAdapter(adapter);
    }

    @Override
    public void onNewsClicked(NewsItem newsItem) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(((ViewGroup) getView().getParent()).getId(), NewsDetailsFragment.newInstance(newsItem));
        transaction.addToBackStack(null);
        transaction.commit();
    }

    /* @Override
    public void onStart() {
        super.onStart();



        new Thread(new Runnable() {
            @Override
            public void run() {
                db = ((NewsReader) getApplication()).getDb();
                List<NewsItem> list = db.newsItemDao().getAll();
                loadFeed();
            }
        }).start();
    }


    public void loadFeed() {
        retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.feed_url))
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .build();
        webservice = retrofit.create(Webservice.class);

        Call<NewsFeed> feed = webservice.getNewsFeed();
        feed.enqueue(new Callback<NewsFeed>() {
            @Override
            public void onResponse(Call<NewsFeed> call, Response<NewsFeed> response) {
                NewsFeed feed = response.body();
                //new backgroundDatabase(feed.getNewsItemList()).execute();
            }

            @Override
            public void onFailure(Call<NewsFeed> call, Throwable t) {

            }
        });
    }

    public class backgroundDatabase extends AsyncTask<Void, Void, Void> {

        private List<NewsItem> newsItems;

        public backgroundDatabase(List<NewsItem> newsItems) {
            this.newsItems = newsItems;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            if(newsItems != null) {
                for (int i = 0; i < newsItems.size(); i++) {
                    NewsItem test = db.newsItemDao().getItem(newsItems.get(i).getGuid());
                    if(test == null)
                        db.newsItemDao().insert(newsItems.get(i));
                    else
                        newsItems.get(i).setRead(test.getRead());
                }
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void id) {

            NewsRecyclerviewAdapter adapter = new NewsRecyclerviewAdapter(
                    this,
                    newsItems);
            listNews.setAdapter(adapter);
            listNews.setLayoutManager(new LinearLayoutManager(MainActivity.this));
            listNews.setItemAnimator(new DefaultItemAnimator());
            listNews.setAdapter(adapter);
        }
    }
 */


}
