package com.example.test;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.test.model.User;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Optional;


public class AppViewModel extends ViewModel {

    public static final String USER_KEY = "user";
    private final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    private final MutableLiveData<User> userLiveData = new MutableLiveData<>();

    public void loadUser() {
        databaseReference.child("users").child(getUid()).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                userLiveData.postValue(task.getResult().getValue(User.class));
            }
        });
    }

    public LiveData<User> observeUser() {
        return userLiveData;
    }


    private User user = null;

    public String getUid() {
        return Optional.ofNullable(firebaseAuth.getCurrentUser()).map(FirebaseUser::getUid).orElse("");

    }

    public void getRole(OnSuccessListener<Integer> onSuccessListener) {
        if (user == null) {
            databaseReference.child("users").child(getUid()).get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    user = task.getResult().getValue(User.class);
                    onSuccessListener.onSuccess(Optional.ofNullable(user).map(User::getRole).orElse(User.USER));
                }
            });
        } else {
            onSuccessListener.onSuccess(user.getRole());
        }
    }

    public User getUser() {
        if (user == null) {
            databaseReference.child("users").child(getUid()).get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    user = task.getResult().getValue(User.class);
                }
            });
        }
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void logout() {
        firebaseAuth.signOut();
    }
}
