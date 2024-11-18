package com.example.test.ui.dashboard;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
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

        adapter = new CategoryAdapter(dashboardViewModel.getCategories());  // Gán listener ở đây

        binding.recyclerView.setAdapter(adapter);

        return binding.getRoot();
    }

}
