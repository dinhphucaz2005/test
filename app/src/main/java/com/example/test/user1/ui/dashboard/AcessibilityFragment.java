package com.example.test.user1.ui.dashboard;

import static com.example.test.model.Article.PRIVATE_ACCESSIBILITY;
import static com.example.test.model.Article.PUBLIC_ACCESSIBILITY;
import static com.example.test.model.Article.RESTRICTED_ACCESSIBILITY;

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