package com.example.test.user.ui.dashboard;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.test.databinding.FragmentWeightBinding;

public class WeightFragment extends Fragment {

    private FragmentWeightBinding binding;
    private DashboardViewModel dashboardViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentWeightBinding.inflate(inflater);
        dashboardViewModel = new ViewModelProvider(requireActivity()).get(DashboardViewModel.class);

        setEvent();

        return binding.getRoot();
    }

    private void setEvent() {
        binding.btnFinish.setOnClickListener(view -> {
            dashboardViewModel.setWeight(Integer.parseInt(binding.editTextWeight.getText().toString()));
            requireActivity().onBackPressed();
        });
    }
}