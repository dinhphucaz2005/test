package com.example.test.user.ui.dashboard;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.test.R;

import java.util.ArrayList;
import java.util.List;


public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ItemViewHolder> {

    public List<String> getSelectedCategory() {
        List<String> result = new ArrayList<>();
        for (Item item : itemList) {
            if (item.isTicked()) {
                result.add(item.getName());
            }
        }
        return result;
    }

    public static class Item {
        private final String name;
        private boolean ticked;

        public Item(String name, boolean ticked) {
            this.name = name;
            this.ticked = ticked;
        }

        public String getName() {
            return name;
        }

        public void setTicked(boolean ticked) {
            this.ticked = ticked;
        }

        public boolean isTicked() {
            return ticked;
        }
    }

    private final List<Item> itemList;

    @SuppressLint("NewApi")
    public CategoryAdapter(List<String> itemList) {
        this.itemList = itemList.stream().map(name -> new Item(name, false)).toList();
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_category_item, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        Item item = itemList.get(position);

        holder.textView.setText(item.getName());
        holder.checkbox.setChecked(item.isTicked());

        holder.itemView.setOnClickListener(v -> {
            item.setTicked(!item.isTicked());
            holder.checkbox.setChecked(item.isTicked());
        });

        holder.checkbox.setOnCheckedChangeListener((buttonView, isChecked) -> item.setTicked(isChecked));
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
            textView = itemView.findViewById(R.id.text_view);
            checkbox = itemView.findViewById(R.id.checkbox);
        }
    }
}
