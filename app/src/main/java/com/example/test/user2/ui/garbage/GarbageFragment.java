package com.example.test.user2.ui.garbage;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.test.databinding.FragmentGarbageBinding;
import com.example.test.databinding.FragmentNotificationsBinding;
import com.example.test.user2.OtherViewModel;

public class GarbageFragment extends Fragment {

    private FragmentGarbageBinding binding;
    private OtherViewModel otherViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        otherViewModel = new ViewModelProvider(requireActivity()).get(OtherViewModel.class);
        binding = FragmentGarbageBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
        otherViewModel = null;
    }
}