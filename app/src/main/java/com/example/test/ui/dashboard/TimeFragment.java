package com.example.test.ui.dashboard;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.test.R;
import com.example.test.databinding.FragmentTimeBinding;

import java.util.Objects;

public class TimeFragment extends Fragment {

    FragmentTimeBinding binding;
    DashboardViewModel dashboardViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentTimeBinding.inflate(inflater, container, false);
        dashboardViewModel = new ViewModelProvider(requireActivity()).get(DashboardViewModel.class);
        setEvents();
        return binding.getRoot();
    }

    private void setEvents() {
        binding.btnBack.setOnClickListener(v -> requireActivity().onBackPressed());
        binding.btnFinish.setOnClickListener(v -> {
            dashboardViewModel.setDate(binding.edtTime.getText().toString());
            dashboardViewModel.setDateDescription(Objects.requireNonNull(binding.noteEditText.getText()).toString());
            requireActivity().onBackPressed();
        });
    }
}