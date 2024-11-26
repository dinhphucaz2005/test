package com.example.test.staff.ui.calendar;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.test.R;
import com.example.test.databinding.FragmentCalendarBinding;
import com.example.test.databinding.ItemDayBinding;
import com.example.test.AppViewModel;
import com.example.test.model.Article;
import com.example.test.model.Task;

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
            Bundle bundle = new Bundle();
            bundle.putSerializable(Article.ARTICLE_ID, task.getArticleId());
            NavController navController = Navigation.findNavController(binding.getRoot());
            navController.navigate(R.id.staff_action_calendarFragment_to_taskDetailFragment, bundle);
        });
        binding.rvTasks.setAdapter(taskAdapter);
        calendarViewModel.getTasks().observe(getViewLifecycleOwner(), tasks -> {
            if (tasks == null || tasks.isEmpty()) {
                binding.rvTasks.setVisibility(View.GONE);
                binding.tvNoTask.setVisibility(View.VISIBLE);
            } else {
                taskAdapter.submitList(tasks);
                binding.rvTasks.setVisibility(View.VISIBLE);
                binding.tvNoTask.setVisibility(View.GONE);
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void initCalendar() {
        List<ItemDayBinding> bindings = new ArrayList<>();
        bindings.add(binding.sun);
        bindings.add(binding.mon);
        bindings.add(binding.tue);
        bindings.add(binding.wed);
        bindings.add(binding.thu);
        bindings.add(binding.fir);
        bindings.add(binding.sat);
        @DrawableRes int background = R.drawable.blue_bg;
        @DrawableRes int backgroundSelected = R.drawable.blue_bg_selected;

        for (ItemDayBinding itemDayBinding : bindings) {
            itemDayBinding.containerBg.setBackgroundResource(background);
        }

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
            bindings.get(i).tvDayOfMonth.setText(dayOfMonths.get(i) + "");
            bindings.get(i).tvDayOfWeek.setText(dayOfWeeks.get(i));
            int dayOfMonth = dayOfMonths.get(i);
            final int finalI = i;
            bindings.get(i).getRoot().setOnClickListener(v -> {
                for (ItemDayBinding itemDayBinding : bindings) {
                    itemDayBinding.containerBg.setBackgroundResource(background);
                }
                bindings.get(finalI).containerBg.setBackgroundResource(backgroundSelected);
                calendarViewModel.loadTasks(dayOfMonth);
            });
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}