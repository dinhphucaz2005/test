package com.example.test.model;

import androidx.annotation.NonNull;

public class TaskNotification extends Notification {
    String taskId;

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

    public TaskNotification(String taskId) {
        super();
        this.taskId = taskId;
        dataCollectFormated = "";
    }

    public TaskNotification() {
        super();
        this.taskId = "";
        dataCollectFormated = "";
    }
}
