package com.example.test.user.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.test.databinding.FragmentNotifyBinding;
import com.example.test.staff.ui.notify.UsersViewModel;

public class HomeFragment extends Fragment {
    private FragmentNotifyBinding binding;
    private UsersViewModel usersViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentNotifyBinding.inflate(inflater, container, false);
        usersViewModel = new ViewModelProvider(this).get(UsersViewModel.class);
        return binding.getRoot();
    }
}
