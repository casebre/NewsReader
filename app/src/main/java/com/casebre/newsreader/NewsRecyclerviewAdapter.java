package com.casebre.newsreader;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class NewsRecyclerviewAdapter extends RecyclerView.Adapter<NewsRecyclerviewAdapter.ViewHolder>{

    private Context context;
    private OnNewsClick listener;
    private List<NewsItem> newsItems;

    public interface OnNewsClick {
        void onNewsClicked(NewsItem newsItem);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.textview_title)
        TextView txtNewsTitle;

        @BindView(R.id.textview_datetime)
        TextView txtPublishedDateTime;

        @BindView(R.id.textview_description)
        TextView txtDescription;

        @BindView(R.id.imageview_news)
        ImageView imgNews;

        @BindView(R.id.cardview_news)
        CardView cardView;

        @BindView(R.id.linearlayout_read)
        LinearLayout linearLayoutRead;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public NewsRecyclerviewAdapter(Context context, List<NewsItem> newsItems, OnNewsClick listener) {
        this.context = context;
        this.newsItems = newsItems;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.row_news, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        Glide.with(context)
                .load(newsItems.get(position).getUrl())
                .into(holder.imgNews);

        holder.txtNewsTitle.setText(newsItems.get(position).getTitle());
        holder.txtPublishedDateTime.setText(newsItems.get(position).getPublishedDate());
        holder.txtDescription.setText(newsItems.get(position).getDescription());
        holder.cardView.setOnClickListener(v -> listener.onNewsClicked(newsItems.get(position)));
        if(newsItems.get(position).getRead())
            holder.linearLayoutRead.setVisibility(View.VISIBLE);
    }

    @Override
    public int getItemCount() {
        return newsItems.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

}
