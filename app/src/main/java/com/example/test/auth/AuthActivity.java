package com.example.test.auth;


import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.test.R;
import com.example.test.databinding.ActivityAuthBinding;

public class AuthActivity extends AppCompatActivity {

    ActivityAuthBinding binding;
    AuthViewModel authViewModel;
    NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityAuthBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        authViewModel = new ViewModelProvider(this).get(AuthViewModel.class);
        navController = Navigation.findNavController(this, R.id.nav_host_fragment_auth);
    }

}