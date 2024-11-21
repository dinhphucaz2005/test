package com.example.test.auth.signin;

import android.content.Intent;
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
import com.example.test.admin.AdminActivity;
import com.example.test.auth.AuthViewModel;
import com.example.test.databinding.FragmentSignInBinding;
import com.example.test.model.User;
import com.example.test.user1.MainActivity;
import com.example.test.user2.OtherActivity;

public class SignInFragment extends Fragment {

    private FragmentSignInBinding binding;
    private AuthViewModel authViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentSignInBinding.inflate(inflater, container, false);
        authViewModel = new ViewModelProvider(requireActivity()).get(AuthViewModel.class);

        setEvents();

        return binding.getRoot();
    }

    private void setEvents() {
        binding.signInButton.setOnClickListener(v -> {
            String email = binding.email.getText().toString();
            String password = binding.password.getText().toString();
            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(requireActivity(), "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                return;
            }
            authViewModel.signIn(email, password, role -> {

                Intent intent = switch (role) {
                    case User.ADMIN -> new Intent(requireActivity(), AdminActivity.class);
                    case User.STAFF -> new Intent(requireActivity(), OtherActivity.class);
                    default -> new Intent(requireActivity(), MainActivity.class);
                };

                startActivity(intent);
                requireActivity().finish();
            }, e -> Toast.makeText(requireActivity(), "Đăng nhập thất bại", Toast.LENGTH_SHORT).show());
        });

        binding.signUpText.setOnClickListener(v -> {
            NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_auth);
            navController.navigate(R.id.action_signInFragment_to_signUpFragment);
        });
    }
}