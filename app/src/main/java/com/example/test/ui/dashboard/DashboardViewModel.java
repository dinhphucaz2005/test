package com.example.test.ui.dashboard;

import android.net.Uri;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

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

    private StorageReference storageReference = FirebaseStorage.getInstance().getReference();
    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

    private final MutableLiveData<Article> _article = new MutableLiveData<>(new Article());


    public void setTitle(String title) {
        Objects.requireNonNull(_article.getValue()).setTitle(title);
    }

    public void setWeight(int weight) {
        Objects.requireNonNull(_article.getValue()).setWeightKgs(weight);
    }

    private void setImageUrls(List<String> imageUrls) {
        Objects.requireNonNull(_article.getValue()).setImageUrls(imageUrls);
    }

    void setDate(String date) {
        Objects.requireNonNull(_article.getValue()).setDate(date);
    }

    void setDateDescription(String dateDescription) {
        Objects.requireNonNull(_article.getValue()).setDateDescription(dateDescription);
    }


    public void setPhoneNumber(String phoneNumber) {
        Objects.requireNonNull(_article.getValue()).setPhoneNumber(phoneNumber);
    }

    public void setAccessibility(String accessibility) {
        Objects.requireNonNull(_article.getValue()).setAccessibility(accessibility);
    }

    public void upload() {
        if (imageUrls.isEmpty()) {
            Log.d("Upload", "upload: No images selected");
            return;
        }
        Article article = Objects.requireNonNull(_article.getValue());
        article.setId(UUID.randomUUID().toString());
        article.setUserId("Current User");
        article.setImageUrls(imageUrls);
        databaseReference.child("articles").push().setValue(article);
    }

    private final List<String> categories = List.of("Sống xanh", "Hành động xanh", "Lối sống xanh", "Phân loại rác", "Tặng đồ cũ", "Tặng đồ ăn");

    public List<String> getCategories() {
        return categories;
    }

    public LiveData<Article> getArticle() {
        return _article;
    }

    private final List<String> imageUrls = new ArrayList<>();

    public void addSelectedImages(List<Uri> selectedImages) {
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
}
