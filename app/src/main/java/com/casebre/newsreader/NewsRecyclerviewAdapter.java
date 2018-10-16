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


public class NewsRecyclerviewAdapter extends RecyclerView.Adapter<NewsRecyclerviewAdapter.ViewHolder>{

    private Context context;
    private OnNewsClick listener;
    private List<NewsItem> newsItems;

    public interface OnNewsClick {
        void onNewsClicked(NewsItem newsItem);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txtNewsTitle, txtPublishedDateTime, txtDescription;
        private ImageView imgNews;
        private CardView cardView;
        private LinearLayout linearLayoutRead;

        public ViewHolder(View view) {
            super(view);
            txtNewsTitle = view.findViewById(R.id.textview_title);
            txtPublishedDateTime = view.findViewById(R.id.textview_datetime);
            txtDescription = view.findViewById(R.id.textview_description);
            imgNews = view.findViewById(R.id.imageview_news);
            cardView = view.findViewById(R.id.cardview_news);
            linearLayoutRead = view.findViewById(R.id.linearlayout_read);
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
