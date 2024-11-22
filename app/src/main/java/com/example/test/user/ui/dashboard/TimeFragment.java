package com.example.test.user.ui.dashboard;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.test.databinding.FragmentTimeBinding;

import java.util.Calendar;
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
            DatePicker datePicker = binding.datePicker;

            int day = datePicker.getDayOfMonth();
            int month = datePicker.getMonth();
            int year = datePicker.getYear();


            TimePicker timePicker = binding.timePicker;
            int hour = timePicker.getHour();
            int minute = timePicker.getMinute();

            Calendar calendar = Calendar.getInstance();
            calendar.set(year, month, day, hour, minute, 0);

            long timeInMillis = calendar.getTimeInMillis();
            dashboardViewModel.setTime(timeInMillis, message -> Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show());
            requireActivity().onBackPressed();
        });
    }
}