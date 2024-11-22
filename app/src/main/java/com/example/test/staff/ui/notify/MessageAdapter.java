package com.example.test.staff.ui.notify;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.test.databinding.LayoutMessageItemBinding;
import com.example.test.model.TextMessage;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {

    private List<TextMessage> messages;
    private final String senderId;

    public MessageAdapter(List<TextMessage> messages, String senderId) {
        this.messages = messages;
        this.senderId = senderId;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutMessageItemBinding binding = LayoutMessageItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TextMessage message = messages.get(position);

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        if (message.getSender().equals(senderId)) {
            params.addRule(RelativeLayout.ALIGN_PARENT_END);
        } else {
            params.addRule(RelativeLayout.ALIGN_PARENT_START);
        }
        holder.binding.messageText.setLayoutParams(params);

        holder.binding.messageText.setText(messages.get(position).getText());
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void updateData(List<TextMessage> textMessages) {
        this.messages = textMessages;
        notifyDataSetChanged();
    }

    public void addNewMessage(TextMessage textMessage) {
        messages.add(textMessage);
        notifyItemInserted(messages.size() - 1);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final LayoutMessageItemBinding binding;

        public ViewHolder(LayoutMessageItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
