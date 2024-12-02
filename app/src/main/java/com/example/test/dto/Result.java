package com.example.test.dto;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Result {
    @SerializedName("formatted_address")
    private String formattedAddress;
    private List<String> types;
    private String name;
    private Geometry geometry;
    private PlusCode plusCode;
    private Compound compound;

    @SerializedName("place_id")
    private String placeId;
    private String url;

    public String getFormattedAddress() {
        return formattedAddress;
    }

    public void setFormattedAddress(String value) {
        this.formattedAddress = value;
    }

    public List<String> getTypes() {
        return types;
    }

    public void setTypes(List<String> value) {
        this.types = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String value) {
        this.name = value;
    }

    public Geometry getGeometry() {
        return geometry;
    }

    public void setGeometry(Geometry value) {
        this.geometry = value;
    }

    public PlusCode getPlusCode() {
        return plusCode;
    }

    public void setPlusCode(PlusCode value) {
        this.plusCode = value;
    }

    public Compound getCompound() {
        return compound;
    }

    public void setCompound(Compound value) {
        this.compound = value;
    }

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String value) {
        this.placeId = value;
    }

    public String geturl() {
        return url;
    }

    public void seturl(String value) {
        this.url = value;
    }
}
