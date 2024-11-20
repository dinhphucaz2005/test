package com.example.test.user2;

import android.os.Bundle;

import com.example.test.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.test.databinding.ActivityOtherBinding;

public class OtherActivity extends AppCompatActivity {

    private ActivityOtherBinding binding;
    private OtherViewModel otherViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityOtherBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        otherViewModel = new ViewModelProvider(this).get(OtherViewModel.class);
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_other);
        NavigationUI.setupWithNavController(binding.otherNavView, navController);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
        otherViewModel = null;
    }
}