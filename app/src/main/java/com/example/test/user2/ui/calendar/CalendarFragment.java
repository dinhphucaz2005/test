package com.example.test.user2.ui.calendar;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.test.databinding.FragmentCalendarBinding;
import com.example.test.databinding.ItemDayBinding;
import com.example.test.user2.AppViewModel;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CalendarFragment extends Fragment {

    private static final int DAY_IN_WEEK = 7;
    private FragmentCalendarBinding binding;
    private AppViewModel appViewModel;
    private CalendarViewModel calendarViewModel;
    private TaskAdapter taskAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        appViewModel = new ViewModelProvider(requireActivity()).get(AppViewModel.class);
        calendarViewModel = new ViewModelProvider(requireActivity()).get(CalendarViewModel.class);
        binding = FragmentCalendarBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initCalendar();
        initRv();

    }

    private void initRv() {
        taskAdapter = new TaskAdapter(new ArrayList<>(), task -> {

        });
        binding.rvTasks.setAdapter(taskAdapter);
        calendarViewModel.getTasks().observe(getViewLifecycleOwner(), tasks -> {
            taskAdapter.submitList(tasks);
        });
    }

    @SuppressLint("SetTextI18n")
    private void initCalendar() {
        List<ItemDayBinding> items = new ArrayList<>();
        items.add(binding.sun);
        items.add(binding.mon);
        items.add(binding.tue);
        items.add(binding.wed);
        items.add(binding.thu);
        items.add(binding.fir);
        items.add(binding.sat);

        LocalDate today = LocalDate.now();
        LocalDate startOfWeek = today.minusDays(today.getDayOfWeek().getValue() % 7);


        ArrayList<Integer> dayOfMonths = new ArrayList<>(DAY_IN_WEEK);
        ArrayList<String> dayOfWeeks = new ArrayList<>(DAY_IN_WEEK);

        for (int i = 0; i < DAY_IN_WEEK; i++) {
            LocalDate day = startOfWeek.plusDays(i);

            String dayOfWeek = day.getDayOfWeek().getDisplayName(TextStyle.SHORT, new Locale("en", "US"));
            int dayOfMonth = day.getDayOfMonth();
            dayOfMonths.add(dayOfMonth);
            dayOfWeeks.add(dayOfWeek);
        }

        for (int i = 0; i < DAY_IN_WEEK; i++) {
            items.get(i).tvDayOfMonth.setText(dayOfMonths.get(i) + "");
            items.get(i).tvDayOfWeek.setText(dayOfWeeks.get(i));
            int dayOfMonth = dayOfMonths.get(i);
            items.get(i).getRoot().setOnClickListener(v -> calendarViewModel.loadTasks(dayOfMonth));
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}