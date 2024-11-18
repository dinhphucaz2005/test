package com.example.test.ui.dashboard;

import android.widget.LinearLayout;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class DashboardViewModel extends ViewModel {

    private String title;
    private String weight;
    private String date;
    private String dateDescription;
    private String phoneNumber;

    public void setTitle(String title) {
        this.title = title;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    private void setDate(String date) {
        this.date = date;
    }

    private void setDateDescription(String dateDescription) {
        this.dateDescription = dateDescription;
    }

    private final MutableLiveData<String> aPublic = new MutableLiveData<>(AcessibilityFragment.PUBLIC_ACCESSIBILITY);

    public LiveData<String> getAccessibility() {
        return aPublic;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setAccessibility(String aPublic) {
        this.aPublic.setValue(aPublic);
    }

    public void changeCategory(Category item) {
        for (Category category : categories)
            category.setTicked(category.getName().equals(item.getName()));
    }


    public static class Category {

        private final String name;
        private boolean isTicked = false;


        Category(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public boolean isTicked() {
            return isTicked;
        }

        public void setTicked(boolean ticked) {
            isTicked = ticked;
        }
    }

    private final List<Category> categories = List.of(
            new Category("Sống xanh"),
            new Category("Hành động xanh"),
            new Category("Lối sống xanh"),
            new Category("Phân loại rác"),
            new Category("Tặng đồ cũ"),
            new Category("Tặng đồ ăn")
    );

    public List<Category> getCategories() {
        return categories;
    }
}
