package com.example.test.user2;

import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Optional;


public class OtherViewModel extends ViewModel {
    private final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    public String getUid() {
        return Optional.ofNullable(firebaseAuth.getCurrentUser())
                .map(FirebaseUser::getUid).orElse("");

    }
}
