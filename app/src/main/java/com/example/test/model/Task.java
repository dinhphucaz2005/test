package com.example.test.model;

import android.graphics.CornerPathEffect;

import java.util.Random;
import java.util.UUID;

public class Task {

    public static final String TASK_ID = "TASK_ID";
    private String id;
    private String articleId;
    private String userId;
    private String title;
    private Long dateCollected;
    private Boolean inProgress;
    private Coordinate coordinate;

    public static Task fromArticle(Article article, String title, Boolean isRandom) {
        double lat = article.getCoordinate().getLatitude();
        double lon = article.getCoordinate().getLongitude();

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
        task.inProgress = false;
        task.coordinate = new Coordinate(lat, lon);
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
        this.inProgress = true;
        this.coordinate = new Coordinate();
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }
}
