package com.example.test.model;

public class Task {

    private String id;
    private String articleId;
    private String userId;
    private String title;
    private Long dateCollected;
    private Boolean inProgress;

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
    }

    public Task(String id, String articleId, String userId, String title, Long dateCollected) {
        this.id = id;
        this.articleId = articleId;
        this.userId = userId;
        this.title = title;
        this.dateCollected = dateCollected;
        this.inProgress = true;
    }
}
