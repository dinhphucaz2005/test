package com.example.test.user2.ui.gift;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.test.databinding.FragmentGiftBinding;
import com.example.test.user2.AppViewModel;

public class GiftFragment extends Fragment {

    private FragmentGiftBinding binding;
    private AppViewModel appViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentGiftBinding.inflate(inflater, container, false);
        appViewModel = new ViewModelProvider(requireActivity()).get(AppViewModel.class);
        return binding.getRoot();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
        appViewModel = null;
    }
}