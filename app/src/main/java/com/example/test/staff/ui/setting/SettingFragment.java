package com.example.test.staff.ui.setting;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.test.auth.AuthActivity;
import com.example.test.databinding.FragmentCurUserBinding;
import com.example.test.AppViewModel;
import com.example.test.model.User;

import java.util.Optional;

public class SettingFragment extends Fragment {

    private FragmentCurUserBinding binding;
    private AppViewModel appViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        appViewModel = new ViewModelProvider(requireActivity()).get(AppViewModel.class);
        binding = FragmentCurUserBinding.inflate(inflater, container, false);

        setEvents();
        observeData();

        return binding.getRoot();
    }

    @SuppressLint("SetTextI18n")
    private void observeData() {
        appViewModel.observeUser().observe(getViewLifecycleOwner(), user -> {
            if (user != null) {
                String displayName = Optional.ofNullable(user.getDisplayName()).orElse("Chưa cập nhật");

                String role = switch (Optional.of(user.getRole()).orElse(User.USER)) {
                    case User.ADMIN -> "Quản trị viên";
                    case User.STAFF -> "Nhân viên";
                    default -> "Khách hàng";
                };

                String email = Optional.ofNullable(user.getEmail()).orElse("Chưa cập nhật");

                binding.displayName.setText(displayName);
                binding.tvRoleStaff.setText("Chức vụ: " + role);
                binding.tvEmailStaff.setText("Email: " + email);
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void setEvents() {
        binding.btnBack.setOnClickListener(v -> requireActivity().onBackPressed());
        binding.btnLogOut.setOnClickListener(v -> {
            Intent intent = new Intent(requireActivity(), AuthActivity.class);
            startActivity(intent);
            appViewModel.logout();
            Toast.makeText(requireActivity(), "Đăng xuất thành công", Toast.LENGTH_SHORT).show();
            requireActivity().finish();
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
        appViewModel = null;
    }
}