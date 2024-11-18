package com.example.test.ui.dashboard;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.test.R;

import java.util.List;


public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ItemViewHolder> {

    private final List<DashboardViewModel.Category> itemList;

    public CategoryAdapter(List<DashboardViewModel.Category> itemList) {
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_category_item, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        DashboardViewModel.Category item = itemList.get(position);

        holder.textView.setText(item.getName());
        holder.checkbox.setChecked(item.isTicked());

        holder.itemView.setOnClickListener(v -> {
        });

        holder.checkbox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            item.setTicked(isChecked);
        });
    }

    @Override
    public int getItemCount() {
        return itemList != null ? itemList.size() : 0;
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {

        public TextView textView;
        public CheckBox checkbox;

        public ItemViewHolder(View itemView) {
            super(itemView);
            // Sử dụng findViewById để ánh xạ các view
            textView = itemView.findViewById(R.id.text_view);
            checkbox = itemView.findViewById(R.id.checkbox);
        }
    }
}
