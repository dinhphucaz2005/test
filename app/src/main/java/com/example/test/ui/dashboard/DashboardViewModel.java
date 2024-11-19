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

import java.util.List;
import java.util.Objects;

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

    private void setDate(String date) {
        Objects.requireNonNull(_article.getValue()).setDate(date);
    }

    private void setDateDescription(String dateDescription) {
        Objects.requireNonNull(_article.getValue()).setDateDescription(dateDescription);
    }


    public void setPhoneNumber(String phoneNumber) {
        Objects.requireNonNull(_article.getValue()).setPhoneNumber(phoneNumber);
    }

    public void setAccessibility(String accessibility) {
        Objects.requireNonNull(_article.getValue()).setAccessibility(accessibility);
    }

    public void upload() {

    }

    private final List<String> categories = List.of("Sống xanh", "Hành động xanh", "Lối sống xanh", "Phân loại rác", "Tặng đồ cũ", "Tặng đồ ăn");

    public List<String> getCategories() {
        return categories;
    }

    public LiveData<Article> getArticle() {
        return _article;
    }

    public void addSelectedImages(List<Uri> selectedImages) {
        Log.d("FATAL", "addSelectedImages: " + selectedImages.toString());
    }
}
