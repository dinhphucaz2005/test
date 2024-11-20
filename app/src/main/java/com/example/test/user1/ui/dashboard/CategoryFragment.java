package com.example.test.user1.ui.dashboard;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.test.databinding.FragmentCategoryBinding;

public class CategoryFragment extends Fragment {

    FragmentCategoryBinding binding;
    DashboardViewModel dashboardViewModel;
    CategoryAdapter adapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentCategoryBinding.inflate(inflater, container, false);
        dashboardViewModel = new ViewModelProvider(requireActivity()).get(DashboardViewModel.class);

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));

        adapter = new CategoryAdapter(dashboardViewModel.getCategories());

        binding.recyclerView.setAdapter(adapter);
        setEvents();

        return binding.getRoot();
    }

    private void setEvents() {
        binding.btnFinish.setOnClickListener(v -> {
            dashboardViewModel.setCategory(adapter.getSelectedCategory());
            NavController navController = Navigation.findNavController(v);
            navController.popBackStack();
        });
    }

}
