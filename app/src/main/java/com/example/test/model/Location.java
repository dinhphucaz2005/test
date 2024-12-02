package com.example.test.model;

public class Location {
    private String title;
    private String address;
    private String placeId;
    private Coordinate coordinate;

    public Location(String title, String address, String placeId, Coordinate coordinate) {
        this.title = title;
        this.address = address;
        this.placeId = placeId;
        this.coordinate = coordinate;
    }

    public Location() {
        this.title = "";
        this.address = "";
        this.placeId = "";
        this.coordinate = new Coordinate();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }
}
