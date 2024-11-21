package com.example.test.model;

public class TextMessage {
    private final String text;
    private final String sender;
    private final Long timestamp;

    public String getText() {
        return text;
    }

    public String getSender() {
        return sender;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public TextMessage() {
        this.text = "";
        this.sender = "";
        this.timestamp = 0L;
    }

    public TextMessage(String text, String sender, Long timestamp) {
        this.text = text;
        this.sender = sender;
        this.timestamp = timestamp;
    }
}
