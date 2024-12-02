package com.example.test.dto;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class StructuredFormatting {
    private List<Object> mainTextMatchedSubstrings;
    private List<Object> secondaryTextMatchedSubstrings;
    @SerializedName("secondary_text")
    private String secondaryText;
    @SerializedName("main_text")
    private String mainText;

    public List<Object> getMainTextMatchedSubstrings() {
        return mainTextMatchedSubstrings;
    }

    public void setMainTextMatchedSubstrings(List<Object> value) {
        this.mainTextMatchedSubstrings = value;
    }

    public List<Object> getSecondaryTextMatchedSubstrings() {
        return secondaryTextMatchedSubstrings;
    }

    public void setSecondaryTextMatchedSubstrings(List<Object> value) {
        this.secondaryTextMatchedSubstrings = value;
    }

    public String getSecondaryText() {
        return secondaryText;
    }

    public void setSecondaryText(String value) {
        this.secondaryText = value;
    }

    public String getMainText() {
        return mainText;
    }

    public void setMainText(String value) {
        this.mainText = value;
    }
}
