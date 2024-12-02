package com.example.test.auth;

import androidx.lifecycle.ViewModel;

import com.example.test.FirebaseKey;
import com.example.test.model.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Optional;

public class AuthViewModel extends ViewModel {


    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

    public void signIn(String email, String password, OnSuccessListener<Integer> onSuccessListener, OnFailureListener onFailureListener) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener(authResult -> {
                    String userId = Optional.ofNullable(authResult.getUser()).map(FirebaseUser::getUid).orElse("");

                    if (userId.isEmpty()) {
                        onFailureListener.onFailure(new Exception("Người dùng không tồn tại"));
                        return;
                    }


                    databaseReference.child(FirebaseKey.USERS).child(userId).get().addOnSuccessListener(dataSnapshot -> {
                        if (dataSnapshot.exists()) {
                            User user = dataSnapshot.getValue(User.class);
                            if (user != null) {
                                onSuccessListener.onSuccess(user.getRole());
                            } else {
                                onFailureListener.onFailure(new Exception("Người dùng không tồn tại"));
                            }
                        } else {
                            onFailureListener.onFailure(new Exception("Người dùng không tồn tại"));
                        }
                    }).addOnFailureListener(onFailureListener);
                })
                .addOnFailureListener(onFailureListener);
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
            onFailureListener.onFailure(new Exception(e.getMessage()));
        });
    }

    private void addCurrentUser() {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null) {
            databaseReference
                    .child(FirebaseKey.USERS)
                    .child(user.getUid())
                    .setValue(user);
        }
    }
}
