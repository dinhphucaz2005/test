package com.example.test.user.ui.notifications;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.test.databinding.FragmentNotificationsBinding;
import com.example.test.AppViewModel;
import com.example.test.user.ui.dashboard.ArticleAdapter;

import java.util.ArrayList;

public class NotificationsFragment extends Fragment {
    private FragmentNotificationsBinding binding;
    private NotificationsViewModel notificationViewModel;
    private AppViewModel appViewModel;
    private ArticleAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        notificationViewModel = new ViewModelProvider(requireActivity()).get(NotificationsViewModel.class);
        appViewModel = new ViewModelProvider(requireActivity()).get(AppViewModel.class);

        setEvents();

        adapter = new ArticleAdapter(new ArrayList<>(), article -> {
        });
        notificationViewModel.loadData(appViewModel.getUid());

        observeData();

        binding.rvArticles.setAdapter(adapter);
        return binding.getRoot();
    }

    private void observeData() {
        notificationViewModel.observeArticle().observe(getViewLifecycleOwner(), adapter::setArticles);
    }

    private void setEvents() {

    }
}