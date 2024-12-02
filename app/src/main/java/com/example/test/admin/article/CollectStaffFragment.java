package com.example.test.admin.article;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.test.AppViewModel;
import com.example.test.databinding.FragmentCollectStaffBinding;
import com.example.test.model.User;
import com.example.test.staff.ui.chat.UserAdapter;
import com.example.test.staff.ui.chat.UsersViewModel;

import java.util.ArrayList;

public class CollectStaffFragment extends Fragment {

    private FragmentCollectStaffBinding binding;
    private ArticleViewModel articleViewModel;
    private UsersViewModel usersViewModel;
    private UserAdapter staffAdapter;
    private AppViewModel appViewModel;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCollectStaffBinding.inflate(inflater, container, false);
        articleViewModel = new ViewModelProvider(requireActivity()).get(ArticleViewModel.class);
        usersViewModel = new ViewModelProvider(requireActivity()).get(UsersViewModel.class);
        appViewModel = new ViewModelProvider(requireActivity()).get(AppViewModel.class);

        usersViewModel.loadUsers(appViewModel.getUid());

        staffAdapter = new UserAdapter(new ArrayList<>(), user -> {
            articleViewModel.setCollectingStaff(user);
            requireActivity().onBackPressed();
        });

        binding.rvStaff.setAdapter(staffAdapter);

        observeData();
        return binding.getRoot();
    }

    private void observeData() {
        usersViewModel.getUsers().observe(getViewLifecycleOwner(), users -> {
            ArrayList<User> staffs = new ArrayList<>();
            for (User user : users) if (user.getRole() == User.STAFF) staffs.add(user);
            staffAdapter.updateData(staffs);
        });

    }
}