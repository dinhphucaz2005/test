package com.example.test.user2.ui.calendar;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.test.databinding.LayoutTaskItemBinding;
import com.example.test.model.Task;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {

    private final List<Task> tasks;
    private final OnSuccessListener<Task> listener;

    public TaskAdapter(List<Task> tasks, OnSuccessListener<Task> listener) {
        this.tasks = tasks;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutTaskItemBinding binding = LayoutTaskItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Task task = tasks.get(position);
        holder.binding.getRoot().setOnClickListener(v -> listener.onSuccess(task));
        holder.binding.tvTaskName.setText(task.getTitle());
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void submitList(List<Task> tasks) {
        this.tasks.clear();
        this.tasks.addAll(tasks);
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public final LayoutTaskItemBinding binding;

        public ViewHolder(LayoutTaskItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
