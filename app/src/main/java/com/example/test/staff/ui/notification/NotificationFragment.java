package com.example.test.staff.ui.notification;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.test.AppViewModel;
import com.example.test.R;
import com.example.test.databinding.FragmentStaffNotificationBinding;
import com.example.test.model.Article;
import com.example.test.model.Task;

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

        notificationAdapter = new NotificationAdapter(notification -> {
            appViewModel.updateTaskNotification(notification);
            NavController navController = Navigation.findNavController(binding.getRoot());
            Bundle bundle = new Bundle();
            bundle.putSerializable(Task.TASK_ID, notification.getTaskId());
            bundle.putSerializable(Task.DATE_FORMATTED, notification.getDataCollectFormated());
            bundle.putSerializable(Article.ARTICLE_ID, notification.getArticleId());
            navController.navigate(R.id.action_notificationFragment_to_notificationDetailFragment, bundle);
        });
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