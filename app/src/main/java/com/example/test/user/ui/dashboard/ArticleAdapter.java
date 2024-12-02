package com.example.test.user.ui.dashboard;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.test.databinding.LayoutArticleBinding;
import com.example.test.model.Article;
import com.example.test.model.ArticleStatus;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.List;
import java.util.Objects;

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ItemViewHolder> {

    private final List<Article> articles;
    private final OnSuccessListener<Article> listener;

    public ArticleAdapter(List<Article> articles, OnSuccessListener<Article> listener) {
        this.articles = articles;
        this.listener = listener;
    }


    @NonNull
    @Override
    public ArticleAdapter.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutArticleBinding binding = LayoutArticleBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ArticleAdapter.ItemViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ArticleAdapter.ItemViewHolder holder, int position) {
        Article item = articles.get(position);
        holder.binding.getRoot().setOnClickListener(v -> listener.onSuccess(item));
        holder.binding.tvTitle.setText(item.getTitle());
        holder.binding.tvDateCreated.setText(item.getFormattedDateCreated());
        holder.binding.tvStatus.setText(item.getCreatedTaskString());
        Integer status = item.getArticleStatus();
        if (Objects.equals(status, ArticleStatus.IN_PROGRESS)) {
            holder.binding.tvStatus.setTextColor(holder.binding.getRoot().getContext().getResources().getColor(android.R.color.holo_blue_dark));
        } else if (Objects.equals(status, ArticleStatus.DONE)) {
            holder.binding.tvStatus.setTextColor(holder.binding.getRoot().getContext().getResources().getColor(android.R.color.holo_green_dark));
        } else if (Objects.equals(status, ArticleStatus.CANCEL)) {
            holder.binding.tvStatus.setTextColor(holder.binding.getRoot().getContext().getResources().getColor(android.R.color.holo_red_dark));
        }
        if (item.getImageUrls() != null && !item.getImageUrls().isEmpty())
            Glide.with(holder.binding.getRoot()).load(item.getImageUrls().get(0)).into(holder.binding.thumbnail);
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
        public final LayoutArticleBinding binding;

        public ItemViewHolder(LayoutArticleBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
