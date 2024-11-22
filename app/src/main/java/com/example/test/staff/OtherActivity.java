package com.example.test.staff;

import android.os.Bundle;

import com.example.test.AppViewModel;
import com.example.test.R;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.example.test.databinding.ActivityOtherBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class OtherActivity extends AppCompatActivity {

    private ActivityOtherBinding binding;
    private AppViewModel appViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityOtherBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        appViewModel = new ViewModelProvider(this).get(AppViewModel.class);
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_other);
        NavigationUI.setupWithNavController(binding.otherNavView, navController);
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser != null) {
            System.out.println(firebaseUser);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
        appViewModel = null;
    }
}