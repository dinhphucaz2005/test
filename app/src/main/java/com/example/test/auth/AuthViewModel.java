package com.example.test.auth;

import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AuthViewModel extends ViewModel {


    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

    public Task<AuthResult> signIn(String email, String password) {
        return firebaseAuth.signInWithEmailAndPassword(email, password);

    }

    public void signUp(String email, String password, String displayName,
                       OnFailureListener onFailureListener, OnSuccessListener<AuthResult> onSuccessListener) {
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnSuccessListener(authResult -> {
            FirebaseUser user = authResult.getUser();
            if (user != null) {

                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                        .setDisplayName(displayName)
                        .build();

                user.updateProfile(profileUpdates).addOnSuccessListener(aVoid -> {
                    addCurrentUser();
                    onSuccessListener.onSuccess(authResult);
                }).addOnFailureListener(onFailureListener);
            } else {
                onFailureListener.onFailure(new Exception("Người dùng không tồn tại"));
            }
        }).addOnFailureListener(e -> {
            onFailureListener.onFailure(new Exception("Đăng ký thất bại"));
        });
    }

    private void addCurrentUser() {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null) {
            databaseReference
                    .child("users")
                    .child(user.getUid())
                    .setValue(user);
        }
    }
}
