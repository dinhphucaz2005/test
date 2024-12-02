package com.example.test.model;

public class Notification {

    public static final Integer NEW = 0;
    public static final Integer SEEN = 1;

    private String id;
    private String title;
    private String content;
    private Integer status;
    private Long createdAt;

    public Notification() {
        this.id= String.valueOf(System.currentTimeMillis());
        this.createdAt = System.currentTimeMillis();
        this.status = NEW;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
    }


}
