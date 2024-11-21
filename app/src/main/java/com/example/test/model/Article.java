package com.example.test.model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Article {
    private String id;
    private String userId;
    private String title;
    private String accessibility;
    private final List<String> categories;
    private List<String> otherCategories;
    private List<String> imageUrls;
    private int weightKgs;
    private String date;
    private String dateDescription;
    private String location;
    private String phoneNumber;
    private Long dateCreated;
    private Long dateCollected;

    public Long getDateCollected() {
        return dateCollected;
    }

    public void setDateCollected(Long dateCollected) {
        this.dateCollected = dateCollected;
    }

    public static final String PUBLIC_ACCESSIBILITY = "Tất cả kết nối";
    public static final String RESTRICTED_ACCESSIBILITY = "Chỉ bạn bè";
    public static final String PRIVATE_ACCESSIBILITY = "Mình bạn";

    public Article() {
        this.id = "";
        this.userId = "";
        this.title = "";
        this.accessibility = PUBLIC_ACCESSIBILITY;
        this.categories = new ArrayList<>();
        this.otherCategories = new ArrayList<>();
        this.imageUrls = new ArrayList<>();
        this.weightKgs = 0;
        this.date = "";
        this.dateDescription = "";
        this.location = "";
        this.phoneNumber = "";
        this.dateCreated = System.currentTimeMillis();
    }

    public Long getDateCreated() {
        return dateCreated;
    }

    public String getFormattedDateCreated() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date(dateCreated);
        return sdf.format(date);
    }

    public void setDateCreated(Long dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Article(Article other) {
        this.id = other.id;
        this.userId = other.userId;
        this.title = other.title;
        this.accessibility = other.accessibility;
        this.categories = other.categories;
        this.otherCategories = other.otherCategories;
        this.imageUrls = other.imageUrls;
        this.weightKgs = other.weightKgs;
        this.date = other.date;
        this.dateDescription = other.dateDescription;
        this.location = other.location;
        this.phoneNumber = other.phoneNumber;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAccessibility() {
        return accessibility;
    }

    public void setAccessibility(String accessibility) {
        this.accessibility = accessibility;
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories.clear();
        this.categories.addAll(categories);
    }

    public List<String> getOtherCategories() {
        return otherCategories;
    }

    public void setOtherCategories(List<String> otherCategories) {
        this.otherCategories = otherCategories;
    }

    public List<String> getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(List<String> imageUrls) {
        this.imageUrls = imageUrls;
    }

    public int getWeightKgs() {
        return weightKgs;
    }

    public void setWeightKgs(int weightKgs) {
        this.weightKgs = weightKgs;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDateDescription() {
        return dateDescription;
    }

    public void setDateDescription(String dateDescription) {
        this.dateDescription = dateDescription;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
