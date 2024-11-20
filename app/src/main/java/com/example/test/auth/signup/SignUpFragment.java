package com.example.test.auth.signup;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.test.R;
import com.example.test.auth.AuthViewModel;
import com.example.test.databinding.FragmentSignUpBinding;

public class SignUpFragment extends Fragment {

    FragmentSignUpBinding binding;
    AuthViewModel authViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSignUpBinding.inflate(inflater, container, false);
        authViewModel = new ViewModelProvider(requireActivity()).get(AuthViewModel.class);
        setEvents();
        return binding.getRoot();
    }

    private void setEvents() {
        binding.signUpButton.setOnClickListener(v -> {
            String email = binding.email.getText().toString();
            String password = binding.password.getText().toString();
            String confirmPassword = binding.confirmPassword.getText().toString();
            if (email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                Toast.makeText(requireActivity(), "Vui lòng điền tất cả thông tin", Toast.LENGTH_SHORT).show();
            } else if (!password.equals(confirmPassword)) {
                Toast.makeText(requireActivity(), "Mật khẩu không khớp", Toast.LENGTH_SHORT).show();
            } else {
                authViewModel.signUp(email, password).addOnSuccessListener(authResult -> {
                    Toast.makeText(requireActivity(), "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                    NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_auth);
                    navController.popBackStack();
                }).addOnFailureListener(e -> {
                    Toast.makeText(requireActivity(), e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                });
            }
        });
        binding.signInText.setOnClickListener(v -> {
            NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_auth);
            navController.popBackStack();
        });
    }
}