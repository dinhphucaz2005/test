package com.example.test.staff.ui.notify;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.test.databinding.LayoutUserItemBinding;
import com.example.test.model.User;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    private final List<User> users;
    private final OnSuccessListener<User> listener;

    public UserAdapter(List<User> users, OnSuccessListener<User> listener) {
        this.users = users;
        this.listener = listener;
    }

    @NonNull
    @Override
    public UserAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutUserItemBinding binding = LayoutUserItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull UserAdapter.ViewHolder holder, int position) {
        User user = users.get(position);
        holder.binding.getRoot().setOnClickListener(v -> listener.onSuccess(user));
        holder.binding.tvUserName.setText(user.getDisplayName());
        holder.binding.tvUserEmail.setText(user.getEmail());
        Glide.with(holder.binding.getRoot())
                .load("https://th.bing.com/th/id/R.47cecf6ce91d73af7900067efeaacb63?rik=%2btKMy%2fBRVLblKA&pid=ImgRaw&r=0")
                .circleCrop().into(holder.binding.ivUserThumbnail);
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void updateData(List<User> users) {
        this.users.clear();
        this.users.addAll(users);
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final LayoutUserItemBinding binding;

        public ViewHolder(LayoutUserItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
