package com.example.test.dto;

import com.google.gson.annotations.SerializedName;

public class Geometry {
    @SerializedName("location")
    private LocationDto locationDto;

    public LocationDto getLocation() {
        return locationDto;
    }

    public void setLocation(LocationDto value) {
        this.locationDto = value;
    }
}
