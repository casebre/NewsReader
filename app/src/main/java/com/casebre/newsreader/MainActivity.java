package com.casebre.newsreader;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;
import retrofit2.http.GET;

public class MainActivity extends AppCompatActivity implements NewsRecyclerviewAdapter.OnNewsClick {

    private static int READ_NEWS_ITEM = 100;
    private SwipeRefreshLayout swipeLayout;
    private RecyclerView listNews;
    private Retrofit retrofit;
    private Api api;
    private NewsItemDatabase db;

    public interface Api {
        @GET("ctvnews-ca-top-stories-public-rss-1.822009")
        Call<NewsFeed> getNewsFeed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        swipeLayout = findViewById(R.id.swiperefresh);
        listNews = findViewById(R.id.recyclerview_news);
    }

    @Override
    public void onStart() {
        super.onStart();
        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadFeed();
                swipeLayout.setRefreshing(false);
            }
        });

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
                .baseUrl("https://www.ctvnews.ca/rss/")
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .build();
        api = retrofit.create(Api.class);

        Call<NewsFeed> feed = api.getNewsFeed();
        feed.enqueue(new Callback<NewsFeed>() {
            @Override
            public void onResponse(Call<NewsFeed> call, Response<NewsFeed> response) {
                NewsFeed feed = response.body();
                new backgroundDatabase(feed.getNewsItemList()).execute();
            }

            @Override
            public void onFailure(Call<NewsFeed> call, Throwable t) {

            }
        });
    }


    @Override
    public void onNewsClicked(NewsItem newsItem) {
        Intent intent = new Intent(MainActivity.this, NewsDetailsActivity.class);
        intent.putExtra("ITEM", newsItem);
        startActivityForResult(intent, READ_NEWS_ITEM);
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
                    MainActivity.this,
                    newsItems);
            listNews.setAdapter(adapter);
            listNews.setLayoutManager(new LinearLayoutManager(MainActivity.this));
            listNews.setItemAnimator(new DefaultItemAnimator());
            listNews.setAdapter(adapter);
        }
    }

}
