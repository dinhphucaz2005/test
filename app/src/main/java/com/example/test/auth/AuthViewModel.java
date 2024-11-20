package com.example.test.auth;

import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class AuthViewModel extends ViewModel {


    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();


    public Task<AuthResult> signIn(String email, String password) {
        return firebaseAuth.signInWithEmailAndPassword(email, password);

    }

    public Task<AuthResult> signUp(String email, String password) {
        return firebaseAuth.createUserWithEmailAndPassword(email, password);
    }
}
