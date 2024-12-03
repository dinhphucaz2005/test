package com.example.test.model;

import androidx.annotation.NonNull;

public class TaskNotification extends Notification {
    String taskId;
    @NonNull
    String articleId;


    @NonNull
    public String getArticleId() {
        return articleId;
    }

    public void setArticleId(@NonNull String articleId) {
        this.articleId = articleId;
    }

    @NonNull
    public String getDataCollectFormated() {
        return dataCollectFormated;
    }

    public void setDataCollectFormated(@NonNull String dataCollectFormated) {
        this.dataCollectFormated = dataCollectFormated;
    }

    @NonNull
    String dataCollectFormated; // yyyy-MM-dd

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public TaskNotification(String taskId, @NonNull String articleId) {
        super();
        this.taskId = taskId;
        this.articleId = articleId;
        dataCollectFormated = "";
    }

    public TaskNotification() {
        super();
        taskId = "";
        articleId = "";
        dataCollectFormated = "";
    }
}
