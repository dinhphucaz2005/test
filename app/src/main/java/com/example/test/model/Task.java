package com.example.test.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

public class Task {

    public static final String TASK_ID = "task_id";
    public static final String DATE_FORMATTED = "date_formatted";
    private String id;
    private String articleId;
    private String userId;
    private String title;
    private Long dateCollected;
    private String collectingStaffId;
    private Integer status = TaskStatus.IN_PROGRESS;
    private List<String> proofImages = new ArrayList<>();

    public String getCollectingStaffId() {
        return collectingStaffId;
    }

    public void setCollectingStaffId(String collectingStaffId) {
        this.collectingStaffId = collectingStaffId;
    }

    private Coordinate coordinate;

    public static Task fromArticle(Article article, String title, Boolean isRandom, String collectingStaffId) {
        double lat = Optional.ofNullable(article.getLocation()).map(location -> location.getCoordinate().getLatitude()).orElse(0.0);
        double lon = Optional.ofNullable(article.getLocation()).map(location -> location.getCoordinate().getLongitude()).orElse(0.0);

        if (isRandom) {
            double distance = 1000.0;
            Random random = new Random();

            double bearing = random.nextDouble() * 360.0;

            double distanceInKm = distance / 1000.0;

            double deltaLat = distanceInKm / 111.32;

            double deltaLon = distanceInKm / (111.32 * Math.cos(Math.toRadians(lat)));

            lat = lat + deltaLat * Math.sin(Math.toRadians(bearing));
            lon = lon + deltaLon * Math.cos(Math.toRadians(bearing));
        }


        Task task = new Task();
        task.setId(UUID.randomUUID().toString());
        task.setArticleId(article.getId());
        task.setUserId(article.getUserId());
        task.setTitle(title);
        task.setDateCollected(article.getDateCollected());
        task.coordinate = new Coordinate(lat, lon);
        task.collectingStaffId = collectingStaffId;
        return task;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setArticleId(String articleId) {
        this.articleId = articleId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDateCollected(Long dateCollected) {
        this.dateCollected = dateCollected;
    }


    public String getId() {
        return id;
    }

    public String getArticleId() {
        return articleId;
    }

    public String getUserId() {
        return userId;
    }

    public String getTitle() {
        return title;
    }

    public Long getDateCollected() {
        return dateCollected;
    }

    public Task() {
        this.id = "";
        this.articleId = "";
        this.userId = "";
        this.title = "";
        this.dateCollected = 0L;
        this.coordinate = new Coordinate();
        this.collectingStaffId = null;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<String> getProofImages() {
        return proofImages;
    }

    public void setProofImages(List<String> proofImages) {
        this.proofImages = proofImages;
    }
}
