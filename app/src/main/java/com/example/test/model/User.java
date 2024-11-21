package com.example.test.model;

public class User {
    private Boolean anonymous;
    private String displayName;
    private String email;
    private Boolean emailVerified;

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    private String phoneNumber;
    private String uid;
    private int role;
    public static final int ADMIN = 1;
    public static final int USER = 2;
    public static final int STAFF = 3;

    public User() {
        this.anonymous = false;
        this.displayName = "";
        this.email = "";
        this.emailVerified = false;
        this.phoneNumber = "";
        this.uid = "";
        this.role = USER;
    }

    public Boolean getAnonymous() {
        return anonymous;
    }

    public void setAnonymous(Boolean anonymous) {
        this.anonymous = anonymous;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getEmailVerified() {
        return emailVerified;
    }

    public void setEmailVerified(Boolean emailVerified) {
        this.emailVerified = emailVerified;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getRoleString() {
        return "Vai trò: " + switch (role) {
            case ADMIN -> "Admin";
            case USER -> "User";
            case STAFF -> "Staff";
            default -> "Unknown";
        };
    }
}