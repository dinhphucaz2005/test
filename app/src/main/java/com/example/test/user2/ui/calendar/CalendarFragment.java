package com.example.test.user2.ui.calendar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.test.databinding.FragmentCalendarBinding;
import com.example.test.databinding.FragmentHomeBinding;
import com.example.test.user2.OtherViewModel;

public class CalendarFragment extends Fragment {

    private FragmentCalendarBinding binding;
    private OtherViewModel otherViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        otherViewModel = new ViewModelProvider(requireActivity()).get(OtherViewModel.class);
        binding = FragmentCalendarBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
        otherViewModel = null;
    }
}