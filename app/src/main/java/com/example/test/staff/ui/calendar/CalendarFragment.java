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
import com.example.test.helper.DateFormatted;
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
    private @DrawableRes
    final int background = R.drawable.blue_bg;
    private @DrawableRes
    final int backgroundSelected = R.drawable.blue_bg_selected;
    List<ItemDayBinding> itemDayBindings = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        appViewModel = new ViewModelProvider(requireActivity()).get(AppViewModel.class);
        calendarViewModel = new ViewModelProvider(requireActivity()).get(CalendarViewModel.class);
        binding = FragmentCalendarBinding.inflate(inflater, container, false);
        initCalendar();
        setEvents();
        return binding.getRoot();
    }

    private void setEvents() {
        binding.btnBackWeek.setOnClickListener(v -> calendarViewModel.previousWeek());
        binding.btnNextWeek.setOnClickListener(v -> calendarViewModel.nextWeek());
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initRv();
    }

    @Override
    public void onResume() {
        super.onResume();
        observeData();
    }

    private void observeData() {
        calendarViewModel.getLocalDates().observe(getViewLifecycleOwner(), localDates -> {

            LocalDate firstDay = localDates.get(0);
            LocalDate lastDay = localDates.get(DAY_IN_WEEK - 1);
            String dateFormatted = DateFormatted.get(firstDay, lastDay);
            binding.tvWeek.setText(dateFormatted);

            for (int i = 0; i < DAY_IN_WEEK; i++) {
                final int finalI = i;
                LocalDate date = localDates.get(finalI);
                itemDayBindings.get(finalI).tvDayOfMonth.setText(String.valueOf(date.getDayOfMonth()));
                itemDayBindings.get(finalI).tvDayOfWeek.setText(date.getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.ENGLISH));
                String formatted = DateFormatted.get(localDates.get(i));

                if (date.equals(LocalDate.now())) {
                    itemDayBindings.get(finalI).containerBg.setBackgroundResource(backgroundSelected);
                    calendarViewModel.loadTasks(formatted, appViewModel.getUid());
                }

                itemDayBindings.get(finalI).getRoot().setOnClickListener(v -> {
                    for (ItemDayBinding itemDayBinding : itemDayBindings) {
                        itemDayBinding.containerBg.setBackgroundResource(background);
                    }
                    itemDayBindings.get(finalI).containerBg.setBackgroundResource(backgroundSelected);
                    calendarViewModel.loadTasks(formatted, appViewModel.getUid());
                });
            }
        });
    }


    private void initRv() {
        taskAdapter = new TaskAdapter(new ArrayList<>(), task -> {
            Bundle bundle = new Bundle();
            bundle.putSerializable(Article.ARTICLE_ID, task.getArticleId());
            bundle.putSerializable(Task.TASK_ID, task.getId());
            bundle.putSerializable(Task.DATE_FORMATTED, DateFormatted.get(task.getDateCollected()));
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

        itemDayBindings.clear();

        itemDayBindings.add(binding.mon);
        itemDayBindings.add(binding.tue);
        itemDayBindings.add(binding.wed);
        itemDayBindings.add(binding.thu);
        itemDayBindings.add(binding.fir);
        itemDayBindings.add(binding.sat);
        itemDayBindings.add(binding.sun);

        for (ItemDayBinding itemDayBinding : itemDayBindings) {
            itemDayBinding.containerBg.setBackgroundResource(background);
        }


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}