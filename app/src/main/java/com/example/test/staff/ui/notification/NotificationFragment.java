package com.example.test.staff.ui.notification;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.test.AppViewModel;
import com.example.test.databinding.FragmentStaffNotificationBinding;

public class NotificationFragment extends Fragment {

    private FragmentStaffNotificationBinding binding;
    private AppViewModel appViewModel;
    private NotificationAdapter notificationAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentStaffNotificationBinding.inflate(inflater, container, false);
        appViewModel = new ViewModelProvider(requireActivity()).get(AppViewModel.class);
        appViewModel.loadTaskNotification();

        notificationAdapter = new NotificationAdapter(notification -> appViewModel.updateTaskNotification(notification));
        binding.rvNotification.setAdapter(notificationAdapter);
        setEvents();
        observeData();
        return binding.getRoot();
    }

    private void observeData() {
        appViewModel.observeTaskNotifications().observe(getViewLifecycleOwner(), notificationAdapter::setNotifications);
    }

    private void setEvents() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
        appViewModel = null;
    }
}