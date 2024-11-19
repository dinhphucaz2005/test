package com.example.test.ui.dashboard;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.test.R;

import java.util.List;

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ItemViewHolder> {

    private final Context context;
    private final List<Article> articles;

    public ArticleAdapter(Context context, List<Article> articles) {
        this.context = context;
        this.articles = articles;
    }


    @NonNull
    @Override
    public ArticleAdapter.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_article, parent, false);
        return new ArticleAdapter.ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ArticleAdapter.ItemViewHolder holder, int position) {
        Article item = articles.get(position);
        holder.textView.setText(item.getTitle());
        holder.tvDataCreated.setText(item.getFormattedDateCreated());
        if (item.getImageUrls() != null && !item.getImageUrls().isEmpty())
            Glide.with(context).load(item.getImageUrls().get(0)).into(holder.thumbnail);
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setArticles(List<Article> articles) {
        this.articles.clear();
        this.articles.addAll(articles);
        notifyDataSetChanged();
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {

        public TextView textView;
        public TextView tvDataCreated;
        public ImageView thumbnail;

        public ItemViewHolder(View itemView) {
            super(itemView);
            tvDataCreated = itemView.findViewById(R.id.tv_date_created);
            textView = itemView.findViewById(R.id.tv_title);
            thumbnail = itemView.findViewById(R.id.thumbnail);
        }
    }
}
