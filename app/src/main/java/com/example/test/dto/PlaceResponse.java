// Temp.java

// YApi QuickType插件生成，具体参考文档:https://plugins.jetbrains.com/plugin/18847-yapi-quicktype/documentation

package com.example.test.dto;
import java.util.List;

public class PlaceResponse {
    private List<Prediction> predictions;
    private String executionTime;
    private String status;

    public List<Prediction> getPredictions() { return predictions; }
    public void setPredictions(List<Prediction> value) { this.predictions = value; }

    public String getExecutionTime() { return executionTime; }
    public void setExecutionTime(String value) { this.executionTime = value; }

    public String getStatus() { return status; }
    public void setStatus(String value) { this.status = value; }
}
