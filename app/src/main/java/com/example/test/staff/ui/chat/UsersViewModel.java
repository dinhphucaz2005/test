package com.example.test.staff.ui.chat;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.test.model.TextMessage;
import com.example.test.model.User;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class UsersViewModel extends ViewModel {

    private final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    private final MutableLiveData<List<User>> users = new MutableLiveData<>(new ArrayList<>());

    public LiveData<List<User>> getUsers() {
        return users;
    }

    public void loadUsers(String userId) {
        databaseReference.child("users").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                List<User> users = new ArrayList<>();
                for (DataSnapshot snapshot : task.getResult().getChildren()) {
                    User user = snapshot.getValue(User.class);

                    if (user != null && !userId.equals(user.getUid()))
                        users.add(user);
                }
                this.users.postValue(users);
            }
        });
    }

    private final MutableLiveData<List<TextMessage>> messages = new MutableLiveData<>(new ArrayList<>());

    public LiveData<List<TextMessage>> getMessages() {
        return messages;
    }

    public void sendMessage(String text, String senderId, String receiverId) {
        String messagePath = getMessagePath(senderId, receiverId);
        TextMessage message = new TextMessage(text, senderId, System.currentTimeMillis());
        databaseReference.child("messages").child(messagePath).child(message.getTimestamp().toString()).setValue(message);
    }

    private String getMessagePath(String senderId, String receiverId) {
        return senderId.compareTo(receiverId) < 0 ? senderId + receiverId : receiverId + senderId;
    }

    public void loadMessages(String senderId, String receiverId, OnSuccessListener<TextMessage> newMessageListener) {
        String messagePath = getMessagePath(senderId, receiverId);
        messages.postValue(new ArrayList<>());
        databaseReference.child("messages").child(messagePath).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                List<TextMessage> messages = new ArrayList<>();
                for (DataSnapshot snapshot : task.getResult().getChildren()) {
                    TextMessage message = snapshot.getValue(TextMessage.class);
                    if (message != null) messages.add(message);
                }
                this.messages.postValue(messages);
            }
        });
        databaseReference.child("messages").child(messagePath).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                TextMessage message = snapshot.getValue(TextMessage.class);
                if (message != null) {
                    newMessageListener.onSuccess(message);
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void loadUser(String userId, OnSuccessListener<User> userListener) {
        databaseReference.child("users").child(userId).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                User user = task.getResult().getValue(User.class);
                userListener.onSuccess(user);
            }
        });
    }


    private final MutableLiveData<Integer> role = new MutableLiveData<>(User.USER);

    public LiveData<Integer> observeRole() {
        return role;
    }

    public void setRole(String userId, int role) {
        if (userId == null) return;
        if (role < 1 || role > 3) return;
        this.role.postValue(role);
    }

    public void saveRole(String userId, OnSuccessListener<String> successListener) {
        databaseReference
                .child("users")
                .child(userId)
                .child("role")
                .setValue(role.getValue())
                .addOnSuccessListener(aVoid -> successListener.onSuccess("Cập nhật vai trò thành công"));
    }


}
