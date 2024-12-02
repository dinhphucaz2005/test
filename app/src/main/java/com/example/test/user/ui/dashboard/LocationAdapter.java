package com.example.test.user.ui.dashboard;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.test.databinding.LayoutLocationItemBinding;
import com.example.test.model.Location;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;
import java.util.List;

public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.ViewHolder> {


    private final List<Location> locations = new ArrayList<>();
    private final OnSuccessListener<Location> listener;

    public LocationAdapter(OnSuccessListener<Location> listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public LocationAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutLocationItemBinding binding = LayoutLocationItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull LocationAdapter.ViewHolder holder, int position) {
        Location location = locations.get(position);
        holder.binding.tvLocationName.setText(location.getTitle());
        holder.binding.tvLocationAddress.setText(location.getAddress());
        holder.binding.getRoot().setOnClickListener(v -> listener.onSuccess(location));
    }

    @Override
    public int getItemCount() {
        return locations.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setLocations(List<Location> locations) {
        this.locations.clear();
        this.locations.addAll(locations);
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public final LayoutLocationItemBinding binding;

        public ViewHolder(LayoutLocationItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
