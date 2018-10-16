package com.casebre.newsreader.details;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.casebre.newsreader.NewsItem;
import com.casebre.newsreader.R;

public class NewsDetailsActivity extends AppCompatActivity {

    private NewsItem newsItem;
    private WebView webView;
    private RelativeLayout layoutProgressBar;
    private ProgressBar progressBar;
    //private NewsItemDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_details);

        if(getIntent().getSerializableExtra("ITEM") != null)
            newsItem = (NewsItem)getIntent().getSerializableExtra("ITEM");

        //db = ((NewsReader)getApplication()).getDb();

        webView = findViewById(R.id.webview);
        layoutProgressBar = findViewById(R.id.relative_layout_progressbar);

    }

    @Override
    public void onStart() {
        super.onStart();
        startProgressBar();
        if (newsItem != null) {

            webView.getSettings().setJavaScriptEnabled(true);
            webView.setWebChromeClient(new WebChromeClient());

            webView.setWebViewClient(new WebViewClient() {

                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    view.loadUrl(url);
                    newsItem.setRead(true);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            //db.newsItemDao().updateRead(newsItem.getGuid(), true);
                        }
                    }).start();

                    return true;
                }

                public void onPageFinished(WebView view, String url) {
                    stopProgressBar();
                }

            });
            webView.loadUrl(newsItem.getLink());
        }
    }

    public void startProgressBar() {
        layoutProgressBar = findViewById(R.id.relative_layout_progressbar);
        layoutProgressBar.setVisibility(View.VISIBLE);
        progressBar = new ProgressBar(this,null,android.R.attr.progressBarStyleLarge);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(100,100);
        params.addRule(RelativeLayout.CENTER_IN_PARENT);
        layoutProgressBar.addView(progressBar,params);
        progressBar.setVisibility(View.VISIBLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    public void stopProgressBar() {
        layoutProgressBar = findViewById(R.id.relative_layout_progressbar);
        progressBar = new ProgressBar(this,null,android.R.attr.progressBarStyleLarge);
        layoutProgressBar.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }
}
