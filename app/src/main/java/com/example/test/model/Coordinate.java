package com.example.test.model;

public class Coordinate {
    private final double latitude;
    private final double longitude;

    public Coordinate() {
        this.latitude = 0;
        this.longitude = 0;
    }

    public Coordinate(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }
}
