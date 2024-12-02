package com.example.test.admin.user;

import android.os.Bundle;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.test.R;
import com.example.test.databinding.FragmentRoleBinding;
import com.example.test.model.User;
import com.example.test.staff.ui.chat.UsersViewModel;


public class RoleFragment extends Fragment {

    FragmentRoleBinding binding;
    UsersViewModel usersViewModel;
    String userId;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentRoleBinding.inflate(inflater, container, false);
        usersViewModel = new ViewModelProvider(requireActivity()).get(UsersViewModel.class);
        if (getArguments() != null) {
            userId = getArguments().getString("user");
        }
        setEvents();
        observeData();
        return binding.getRoot();
    }

    private void observeData() {
        @DrawableRes int normalBg = R.drawable.light_green_bg;
        @DrawableRes int specialBg = R.drawable.item_day_bg;
        usersViewModel.observeRole().observe(getViewLifecycleOwner(), role -> {
            binding.btnAdmin.setBackgroundResource(normalBg);
            binding.btnStaff.setBackgroundResource(normalBg);
            binding.btnUser.setBackgroundResource(normalBg);
            switch (role) {
                case User.USER -> binding.btnUser.setBackgroundResource(specialBg);
                case User.STAFF -> binding.btnStaff.setBackgroundResource(specialBg);
                case User.ADMIN -> binding.btnAdmin.setBackgroundResource(specialBg);
            }
        });
    }

    private void back(String message) {
        if (message != null) Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show();
        requireActivity().getSupportFragmentManager().popBackStack();
    }

    private void setEvents() {

        binding.btnFinish.setOnClickListener(v -> usersViewModel.saveRole(userId, this::back));
        binding.btnBack.setOnClickListener(v -> back(null));

        binding.btnAdmin.setOnClickListener(v -> usersViewModel.setRole(userId, User.ADMIN));
        binding.btnStaff.setOnClickListener(v -> usersViewModel.setRole(userId, User.STAFF));
        binding.btnUser.setOnClickListener(v -> usersViewModel.setRole(userId, User.USER));
    }
}