package com.example.test.user1.ui.notifications;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.test.databinding.FragmentNotificationsBinding;
import com.example.test.user1.ui.dashboard.ArticleAdapter;

import java.util.ArrayList;

public class NotificationsFragment extends Fragment {
    private FragmentNotificationsBinding binding;
    private NotificationsViewModel notificationViewModel;
    private ArticleAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        notificationViewModel = new ViewModelProvider(requireActivity()).get(NotificationsViewModel.class);
        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        setEvents();
        notificationViewModel.loadData();
        observeData();
        adapter = new ArticleAdapter(requireActivity(), new ArrayList<>());
        binding.rv.setAdapter(adapter);
        return binding.getRoot();
    }

    private void observeData() {
        notificationViewModel.observeArticle().observe(getViewLifecycleOwner(), articles -> {
            adapter.setArticles(articles);
        });
    }

    private void setEvents() {

    }
}