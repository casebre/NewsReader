package com.casebre.newsreader.details;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.casebre.newsreader.NewsItem;
import com.casebre.newsreader.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewsDetailsFragment extends Fragment {

    private final static String NEWS_ITEM_TAG = "NEWS_ITEM";

    private Context context;
    private NewsItem selectedNewsItem;

    @BindView(R.id.webview)
    WebView webView;

    @BindView(R.id.relative_layout_progressbar)
    RelativeLayout layoutProgressBar;

    private ProgressBar progressBar;

    public static Fragment newInstance(NewsItem newsItem) {
        NewsDetailsFragment fragment = new NewsDetailsFragment();
        Bundle args = new Bundle();
        args.putSerializable(NEWS_ITEM_TAG, newsItem);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.context = getActivity();
        if(getArguments() != null) {
            selectedNewsItem = (NewsItem) getArguments().getSerializable(NEWS_ITEM_TAG);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_news_details, container, false);
        ButterKnife.bind(this, view);

        //db = ((NewsReader)getApplication()).getDb();

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        loadWebView();
    }


    public void loadWebView() {
        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(selectedNewsItem.getLink());
    }

    public void startProgressBar() {
        layoutProgressBar.setVisibility(View.VISIBLE);
        progressBar = new ProgressBar(context,null,android.R.attr.progressBarStyleLarge);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(100,100);
        params.addRule(RelativeLayout.CENTER_IN_PARENT);
        layoutProgressBar.addView(progressBar,params);
        progressBar.setVisibility(View.VISIBLE);
        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    public void stopProgressBar() {
        progressBar = new ProgressBar(context,null,android.R.attr.progressBarStyleLarge);
        layoutProgressBar.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);
        getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }


}
