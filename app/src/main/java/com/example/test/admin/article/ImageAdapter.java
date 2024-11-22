package com.example.test.admin.article;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.test.databinding.LayoutImageItemBinding;

import java.util.ArrayList;
import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder> {

    private final List<String> images = new ArrayList<>();

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutImageItemBinding binding = LayoutImageItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String url = images.get(position);
        Glide.with(holder.binding.getRoot()).load(url).into(holder.binding.img);
    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setImages(List<String> images) {
        this.images.clear();
        this.images.addAll(images);
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final LayoutImageItemBinding binding;

        public ViewHolder(LayoutImageItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
