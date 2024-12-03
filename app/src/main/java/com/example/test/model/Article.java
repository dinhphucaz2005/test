package com.example.test.model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class Article {
    public static final String ARTICLE_ID = "articleId";
    private String id;
    private String userId;
    private String title;
    private String accessibility;
    private final List<String> categories;
    private List<String> otherCategories;
    private List<String> imageUrls;
    private int weightKgs;
    private String dateDescription;
    private String phoneNumber;
    private Long dateCreated;
    private Long dateCollected;

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    private Location location;
    public Integer getArticleStatus() {
        return articleStatus;
    }

    public void setArticleStatus(Integer articleStatus) {
        this.articleStatus = articleStatus;
    }

    private Integer articleStatus = ArticleStatus.IN_PROGRESS;

    public String getCreatedTaskString() {
        if (Objects.equals(articleStatus, ArticleStatus.IN_PROGRESS)) return "Đang xử lý";
        if (Objects.equals(articleStatus, ArticleStatus.DONE)) return "Đã xử lý";
        if (Objects.equals(articleStatus, ArticleStatus.CANCEL)) return "Đã hủy";
        return "Không xác định";
    }
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
        this.dateDescription = "";
        this.phoneNumber = "";
        this.dateCreated = System.currentTimeMillis();
        this.dateCollected = null;
        this.location = null;
    }

    public Long getDateCreated() {
        return dateCreated;
    }

    public String getFormattedDateCreated() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date(dateCreated);
        return "Ngày đăng bài: " + sdf.format(date);
    }

    public String getFormattedDateCollect() {
        if (dateCollected == null) return "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date(dateCollected);
        return sdf.format(date);
    }

    public void setDateCreated(Long dateCreated) {
        this.dateCreated = dateCreated;
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
        if (title == null || title.isEmpty()) return "Không có tiêu đề";
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

    public String getDateDescription() {
        return dateDescription;
    }

    public void setDateDescription(String dateDescription) {
        this.dateDescription = dateDescription;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
