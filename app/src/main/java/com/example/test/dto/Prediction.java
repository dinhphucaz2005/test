package com.example.test.dto;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Prediction {
    private String reference;
    private List<String> types;
    private List<Object> matchedSubstrings;
    private boolean hasChildren;
    private List<Term> terms;
    @SerializedName("structured_formatting")
    private StructuredFormatting structuredFormatting;
    private String description;
    private PlusCode plusCode;
    private Compound compound;
    @SerializedName("place_id")
    private String placeid;

    public String getReference() {
        return reference;
    }

    public void setReference(String value) {
        this.reference = value;
    }

    public List<String> getTypes() {
        return types;
    }

    public void setTypes(List<String> value) {
        this.types = value;
    }

    public List<Object> getMatchedSubstrings() {
        return matchedSubstrings;
    }

    public void setMatchedSubstrings(List<Object> value) {
        this.matchedSubstrings = value;
    }

    public boolean getHasChildren() {
        return hasChildren;
    }

    public void setHasChildren(boolean value) {
        this.hasChildren = value;
    }

    public List<Term> getTerms() {
        return terms;
    }

    public void setTerms(List<Term> value) {
        this.terms = value;
    }

    public StructuredFormatting getStructuredFormatting() {
        return structuredFormatting;
    }

    public void setStructuredFormatting(StructuredFormatting value) {
        this.structuredFormatting = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String value) {
        this.description = value;
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

    public String getPlaceid() {
        return placeid;
    }

    public void setPlaceid(String value) {
        this.placeid = value;
    }
}
