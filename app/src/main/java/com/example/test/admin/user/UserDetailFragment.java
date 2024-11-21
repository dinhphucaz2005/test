package com.example.test.admin.user;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.test.R;
import com.example.test.databinding.FragmentUserDetailBinding;
import com.example.test.user2.ui.notify.UsersViewModel;


public class UserDetailFragment extends Fragment {

    FragmentUserDetailBinding binding;
    UsersViewModel usersViewModel;
    String userId;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentUserDetailBinding.inflate(inflater, container, false);
        usersViewModel = new ViewModelProvider(requireActivity()).get(UsersViewModel.class);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null) {
            userId = getArguments().getString("user");
            loadCurUser();
        }
        setEvents();
    }

    private void loadCurUser() {
        if (userId != null) {
            usersViewModel.loadUser(userId, user -> {
                if (user != null) {
                    binding.tvUserName.setText(user.getDisplayName());
                    binding.tvEmail.setText(user.getEmail());
                    binding.tvRole.setText(user.getRoleString());
                }
            });
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        loadCurUser();
    }

    private void setEvents() {
        binding.tvRole.setOnClickListener(v -> {
            NavController navController = Navigation.findNavController(binding.getRoot());
            Bundle bundle = new Bundle();
            bundle.putSerializable("user", userId);
            navController.navigate(R.id.admin_action_userDetailFragment_to_roleFragment2, bundle);
        });
    }
}