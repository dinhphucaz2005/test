package com.example.test.admin.user;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.test.R;
import com.example.test.databinding.FragmentUserBinding;
import com.example.test.user2.AppViewModel;
import com.example.test.user2.ui.notify.UsersViewModel;
import com.example.test.user2.ui.notify.UserAdapter;

import java.util.ArrayList;

public class UserFragment extends Fragment {

    FragmentUserBinding binding;
    UsersViewModel usersViewModel;
    AppViewModel appViewModel;
    UserAdapter userAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentUserBinding.inflate(inflater, container, false);
        usersViewModel = new ViewModelProvider(requireActivity()).get(UsersViewModel.class);
        appViewModel = new ViewModelProvider(requireActivity()).get(AppViewModel.class);

        usersViewModel.loadUsers(appViewModel.getUid());


        userAdapter = new UserAdapter(new ArrayList<>(), user -> {
            NavController navController = Navigation.findNavController(binding.getRoot());
            Bundle bundle = new Bundle();
            bundle.putSerializable("user", user.getUid());
            navController.navigate(R.id.admin_action_userFragment_to_userDetailFragment, bundle);
        });
        binding.rvUsers.setAdapter(userAdapter);
        setEvents();
        observeData();
        return binding.getRoot();
    }

    private void setEvents() {
    }

    private void observeData() {
        usersViewModel.getUsers().observe(getViewLifecycleOwner(), userAdapter::updateData);
    }
}