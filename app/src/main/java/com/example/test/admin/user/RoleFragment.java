package com.example.test.admin.user;

import android.os.Bundle;

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
import com.example.test.user2.ui.notify.UsersViewModel;


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
        return binding.getRoot();
    }

    private void back(String message) {
        Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show();
        requireActivity().getSupportFragmentManager().popBackStack();
    }

    private void setEvents() {
        binding.btnAdmin.setOnClickListener(v -> usersViewModel.setRole(userId, User.ADMIN, this::back));
        binding.btnStaff.setOnClickListener(v -> usersViewModel.setRole(userId, User.STAFF, this::back));
        binding.btnUser.setOnClickListener(v -> usersViewModel.setRole(userId, User.USER, this::back));
    }
}