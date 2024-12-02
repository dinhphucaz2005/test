// CoordinateResponse.java

// YApi QuickType插件生成，具体参考文档:https://plugins.jetbrains.com/plugin/18847-yapi-quicktype/documentation

package com.example.test.dto;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CoordinateResponse {
    private Result result;
    private String status;

    public Result getResult() {
        return result;
    }

    public void setResult(Result value) {
        this.result = value;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String value) {
        this.status = value;
    }
}