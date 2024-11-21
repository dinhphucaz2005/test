package com.example.test.user2.ui.notify;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.test.databinding.FragmentMessageBinding;
import com.example.test.user2.AppViewModel;


public class MessageFragment extends Fragment {

    private FragmentMessageBinding binding;
    private UsersViewModel usersViewModel;
    private AppViewModel appViewModel;
    private MessageAdapter messageAdapter;
    private String receiverId;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentMessageBinding.inflate(inflater, container, false);
        usersViewModel = new ViewModelProvider(requireActivity()).get(UsersViewModel.class);
        appViewModel = new ViewModelProvider(requireActivity()).get(AppViewModel.class);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String senderId = appViewModel.getUid();
        if (getArguments() != null) {
            receiverId = getArguments().getString("user");
            if (senderId != null) {
                messageAdapter = new MessageAdapter(usersViewModel.getMessages().getValue(), senderId);
                usersViewModel.loadMessages(senderId, receiverId, newMessages -> {
                    if (newMessages != null && messageAdapter != null && binding != null) {
                        messageAdapter.addNewMessage(newMessages);
                        binding.rvMessages.scrollToPosition(messageAdapter.getItemCount() - 1);
                    }
                });
            }
        }

        binding.rvMessages.setAdapter(messageAdapter);
        setEvents();
        observeData();
    }

    private void setEvents() {
        binding.messageBoxButton.setOnClickListener(v -> {
            String text = binding.messageBoxTextField.getText().toString();
            if (!text.isEmpty() && receiverId != null) {
                usersViewModel.sendMessage(text, appViewModel.getUid(), receiverId);
            }
        });
    }

    private void observeData() {
        usersViewModel.getMessages().observe(getViewLifecycleOwner(), messageAdapter::updateData);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}