package com.example.test.ui.dashboard;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.test.databinding.FragmentContactBinding;

import java.util.Objects;


public class ContactFragment extends Fragment {

    private FragmentContactBinding binding;
    private DashboardViewModel dashboardViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentContactBinding.inflate(inflater, container, false);
        dashboardViewModel = new ViewModelProvider(requireActivity()).get(DashboardViewModel.class);

        setEvents();

        return binding.getRoot();
    }

    private void setEvents() {
        binding.edtPhoneNumber.setText(Objects.requireNonNull(dashboardViewModel.getArticle().getValue()).getPhoneNumber());
        binding.btnFinish.setOnClickListener(view -> {
            String phoneNumber = binding.edtPhoneNumber.getText().toString();
            dashboardViewModel.setPhoneNumber(phoneNumber);
            requireActivity().onBackPressed();
        });
    }
}