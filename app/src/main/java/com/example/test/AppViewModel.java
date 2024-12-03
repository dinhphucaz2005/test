package com.example.test;

import android.net.Uri;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.test.model.TaskNotification;
import com.example.test.model.TaskStatus;
import com.example.test.model.User;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class AppViewModel extends ViewModel {

    private final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    private final StorageReference storageReference = FirebaseStorage.getInstance().getReference();
    private final MutableLiveData<User> userLiveData = new MutableLiveData<>();


    public void getUserById(String userId, OnSuccessListener<User> onSuccessListener) {
        databaseReference.child(FirebaseKey.USERS).child(userId).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                onSuccessListener.onSuccess(task.getResult().getValue(User.class));
            }
        });
    }

    public void loadUser() {
        databaseReference.child(FirebaseKey.USERS).child(getUid()).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                userLiveData.postValue(task.getResult().getValue(User.class));
            }
        });
    }

    public LiveData<User> observeUser() {
        return userLiveData;
    }


    public String getUid() {
        return Optional.ofNullable(firebaseAuth.getCurrentUser()).map(FirebaseUser::getUid).orElse("");
    }

    public void getRole(OnSuccessListener<Integer> onSuccessListener) {
        if (userLiveData.getValue() == null) {
            databaseReference.child(FirebaseKey.USERS).child(getUid()).get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    User user = task.getResult().getValue(User.class);
                    userLiveData.postValue(user);
                    onSuccessListener.onSuccess(Optional.ofNullable(user).map(User::getRole).orElse(User.USER));
                }
            });
        } else {
            onSuccessListener.onSuccess(userLiveData.getValue().getRole());
        }
    }


    public void logout() {
        firebaseAuth.signOut();
        userLiveData.postValue(null);
    }

    private final MutableLiveData<List<TaskNotification>> notifications = new MutableLiveData<>(new ArrayList<>());

    public LiveData<List<TaskNotification>> observeTaskNotifications() {
        return notifications;
    }

    public void loadTaskNotification() {
        databaseReference.child(FirebaseKey.USERS).child(getUid()).child(FirebaseKey.NOTIFICATIONS).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                List<TaskNotification> notificationList = new ArrayList<>();
                for (DataSnapshot snapshot : task.getResult().getChildren()) {
                    notificationList.add(snapshot.getValue(TaskNotification.class));
                }
                notifications.postValue(notificationList);
            }
        });
    }

    public TaskNotification getTaskNotification(String notificationId) {
        if (notifications.getValue() == null) {
            return null;
        }
        return notifications.getValue().stream().filter(notification -> notification.getId().equals(notificationId)).findFirst().orElse(null);
    }

    public void updateTaskNotification(TaskNotification notification) {
        notification.setStatus(TaskNotification.SEEN);
        databaseReference.child(FirebaseKey.USERS).child(getUid()).child(FirebaseKey.NOTIFICATIONS).child(notification.getId()).setValue(notification).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                List<TaskNotification> notificationList = new ArrayList<>(notifications.getValue());
                notificationList.set(notificationList.indexOf(notification), notification);
                notifications.postValue(notificationList);
            }
        });

        List<TaskNotification> notificationList = new ArrayList<>(notifications.getValue());
        notificationList.set(notificationList.indexOf(notification), notification);
        notifications.postValue(notificationList);
    }

    public void requestAcceptTask(String dateFormatted, String taskId, List<Uri> imageUris, OnSuccessListener<String> listener) {
        if (taskId == null) return;

        new Thread(() -> {
            // upload proof images
            List<String> urls = new ArrayList<>();
            for (int i = 0; i < imageUris.size(); i++) {
                Uri uri = imageUris.get(i);
                StorageReference imageRef = storageReference.child("images/" + uri.getLastPathSegment());

                int finalI = i;
                imageRef.putFile(uri).addOnSuccessListener(taskSnapshot -> {
                    imageRef.getDownloadUrl().addOnSuccessListener(url -> {
                        urls.add(url.toString());
                        if (finalI == imageUris.size() - 1) {
                            databaseReference
                                    .child(FirebaseKey.TASKS)
                                    .child(dateFormatted)
                                    .child(taskId)
                                    .child(FirebaseKey.PROOF_IMAGES)
                                    .setValue(urls);
                        }
                    });
                });
            }

            // update task status
            databaseReference
                    .child(FirebaseKey.TASKS)
                    .child(dateFormatted)
                    .child(taskId)
                    .child(FirebaseKey.TASKS_STATUS)
                    .setValue(TaskStatus.PENDING)
                    .addOnCompleteListener(task -> listener.onSuccess("Request sent"));
        }).start();


    }

    public void cancelTask(String dateFormatted, String taskId, OnSuccessListener<String> listener) {
        if (taskId == null) return;

        databaseReference
                .child(FirebaseKey.TASKS)
                .child(dateFormatted)
                .child(taskId)
                .child(FirebaseKey.TASKS_STATUS)
                .setValue(TaskStatus.CANCEL)
                .addOnCompleteListener(task -> listener.onSuccess("Đã yêu cầu hủy việc thành công"));
    }

    public void acceptTask(String dateFormatted, String taskId, OnSuccessListener<String> listener) {
        if (taskId == null) return;
        databaseReference
                .child(FirebaseKey.TASKS)
                .child(dateFormatted)
                .child(taskId)
                .child(FirebaseKey.TASKS_STATUS)
                .setValue(TaskStatus.DONE)
                .addOnCompleteListener(task -> listener.onSuccess("Đồng ý xác nhận việc thành công"));
    }
}
