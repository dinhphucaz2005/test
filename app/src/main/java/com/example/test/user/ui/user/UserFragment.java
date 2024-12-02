package com.example.test.user.ui.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.test.auth.AuthActivity;
import com.example.test.databinding.FragmentNotificationsBinding;
import com.example.test.AppViewModel;
import com.example.test.model.User;
import com.example.test.user.ui.dashboard.ArticleAdapter;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;

public class UserFragment extends Fragment {
    private FragmentNotificationsBinding binding;
    private UserViewModel notificationViewModel;
    private AppViewModel appViewModel;
    private ArticleAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        notificationViewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);
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

        appViewModel.observeUser().observe(getViewLifecycleOwner(), user -> binding.tvUserName.setText(Optional.ofNullable(user).map(User::getDisplayName).orElse("Chưa cập nhật")));
    }

    private void setEvents() {
        binding.btnBack.setOnClickListener(v -> {
            requireActivity().onBackPressed();
        });
        binding.btnLogOutUser.setOnClickListener(v -> {
            appViewModel.logout();
            Toast.makeText(requireActivity(), "Đăng xuất thành công", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(requireActivity(), AuthActivity.class);
            startActivity(intent);
            requireActivity().finish();
        });
    }
}