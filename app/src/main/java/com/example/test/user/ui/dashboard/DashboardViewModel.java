package com.example.test.user.ui.dashboard;

import android.net.Uri;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.test.FirebaseKey;
import com.example.test.model.Article;
import com.example.test.model.Coordinate;
import com.example.test.model.Location;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class DashboardViewModel extends ViewModel {

    private final StorageReference storageReference = FirebaseStorage.getInstance().getReference();
    private final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

    private final MutableLiveData<Article> _article = new MutableLiveData<>(new Article());


    public void setTitle(String title) {
        Objects.requireNonNull(_article.getValue()).setTitle(title);
    }

    public void setTime(long timeInMillis, OnSuccessListener<String> listener) {
        if (timeInMillis < System.currentTimeMillis() || timeInMillis > System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 7) {
            listener.onSuccess("Thời gian không hợp lệ");
            return;
        }
        Objects.requireNonNull(_article.getValue()).setDateCollected(timeInMillis);
    }

    public void setWeight(int weight) {
        Objects.requireNonNull(_article.getValue()).setWeightKgs(weight);
    }

    private void setImageUrls(List<String> imageUrls) {
        Objects.requireNonNull(_article.getValue()).setImageUrls(imageUrls);
    }

    public void setPhoneNumber(String phoneNumber) {
        Objects.requireNonNull(_article.getValue()).setPhoneNumber(phoneNumber);
    }

    public void setAccessibility(String accessibility) {
        Objects.requireNonNull(_article.getValue()).setAccessibility(accessibility);
    }

    public void upload(String userId, String title, OnSuccessListener<String> listener) {
        if (imageUrls.isEmpty()) {
            listener.onSuccess("Không ảnh nào được chọn");
            return;
        }
        Article article = Objects.requireNonNull(_article.getValue());

        if (article.getLocation() == null) {
            listener.onSuccess("Vui lòng chọn vị trí");
            return;
        }

        article.setTitle(title);
        article.setId(UUID.randomUUID().toString());
        article.setUserId(userId);
        article.setImageUrls(imageUrls);
        article.setDateCreated(System.currentTimeMillis());

        databaseReference.child(FirebaseKey.ARTICLES).child(article.getId()).setValue(article)
                .addOnSuccessListener(var1 -> {
                    _article.setValue(new Article());
                    _selectedImages.setValue(Collections.emptyList());
                    imageUrls.clear();
                    listener.onSuccess("Thành công");
                });
    }

    private final List<String> categories = List.of("Sống xanh", "Hành động xanh", "Lối sống xanh", "Phân loại rác", "Tặng đồ cũ", "Tặng đồ ăn");

    public List<String> getCategories() {
        return categories;
    }

    public LiveData<Article> getArticle() {
        return _article;
    }

    private final List<String> imageUrls = new ArrayList<>();

    private final MutableLiveData<List<Uri>> _selectedImages = new MutableLiveData<>(new ArrayList<>());

    public LiveData<List<Uri>> getSelectedImages() {
        return _selectedImages;
    }

    public void addSelectedImages(List<Uri> selectedImages) {
        _selectedImages.setValue(selectedImages);
        Thread thread = new Thread(() -> {
            List<String> tempImageUrls = new ArrayList<>();
            for (Uri uri : selectedImages) {
                StorageReference imageRef = storageReference.child("images/" + uri.getLastPathSegment());

                imageRef.putFile(uri).addOnSuccessListener(taskSnapshot -> {
                    imageRef.getDownloadUrl().addOnSuccessListener(url -> {
                        synchronized (tempImageUrls) {
                            tempImageUrls.add(url.toString());
                        }

                        if (tempImageUrls.size() == selectedImages.size()) {
                            synchronized (imageUrls) {
                                imageUrls.clear();
                                imageUrls.addAll(tempImageUrls);
                                setImageUrls(imageUrls);
                            }
                        }
                    });
                });
            }
        });
        thread.start();
    }

    public void setCategory(List<String> selectedCategory) {
        Objects.requireNonNull(_article.getValue()).setCategories(selectedCategory);
    }

    public void setLocation(Location location) {
        Objects.requireNonNull(_article.getValue()).setLocation(location);
    }
}
