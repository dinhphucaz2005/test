package com.example.test.ui.dashboard;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.test.databinding.FragmentAcessibilityBinding;

public class AcessibilityFragment extends Fragment {

    static final String PUBLIC_ACCESSIBILITY = "Tất cả kết nối";
    static final String RESTRICTED_ACCESSIBILITY = "Chỉ bạn bè";
    static final String PRIVATE_ACCESSIBILITY = "Mình bạn";

    FragmentAcessibilityBinding binding;
    DashboardViewModel dashboardViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentAcessibilityBinding.inflate(inflater, container, false);
        dashboardViewModel = new ViewModelProvider(requireActivity()).get(DashboardViewModel.class);

        setEvents();

        return binding.getRoot();
    }

    private void setEvents() {
        binding.btnPublic.setOnClickListener(view -> {
            dashboardViewModel.setAccessibility(PUBLIC_ACCESSIBILITY);
            NavController navController = Navigation.findNavController(view);
            navController.popBackStack();
        });
        binding.btnRestricted.setOnClickListener(view -> {
            dashboardViewModel.setAccessibility(RESTRICTED_ACCESSIBILITY);
            NavController navController = Navigation.findNavController(view);
            navController.popBackStack();
        });
        binding.btnPrivate.setOnClickListener(view -> {
            dashboardViewModel.setAccessibility(PRIVATE_ACCESSIBILITY);
            NavController navController = Navigation.findNavController(view);
            navController.popBackStack();
        });
    }
}