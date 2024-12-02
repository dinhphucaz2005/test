package com.example.test.staff.ui.notification;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.test.databinding.LayoutNotificationItemBinding;
import com.example.test.model.Notification;
import com.example.test.model.TaskNotification;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {

    private final List<TaskNotification> notifications = new ArrayList<>();
    private final OnSuccessListener<TaskNotification> listener;

    public NotificationAdapter(OnSuccessListener<TaskNotification> listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public NotificationAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutNotificationItemBinding binding = LayoutNotificationItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationAdapter.ViewHolder holder, int position) {
        TaskNotification notification = notifications.get(position);
        holder.binding.tvNotificationTitle.setText(notification.getTitle());
        holder.binding.tvNotificationDescription.setText(notification.getContent());
        if (Objects.equals(notification.getStatus(), Notification.NEW)) {
            holder.binding.linearLayout.setVisibility(View.VISIBLE);
        } else {
            holder.binding.linearLayout.setVisibility(View.GONE);
        }
        holder.binding.getRoot().setOnClickListener(v -> listener.onSuccess(notification));
    }

    @Override
    public int getItemCount() {
        return notifications.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setNotifications(List<TaskNotification> notifications) {
        this.notifications.clear();
        this.notifications.addAll(notifications);
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public final LayoutNotificationItemBinding binding;

        public ViewHolder(LayoutNotificationItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
