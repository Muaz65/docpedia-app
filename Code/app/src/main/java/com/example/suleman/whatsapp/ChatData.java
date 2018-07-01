package com.example.suleman.whatsapp;



public class ChatData {
    String type;
    String message;
    String time;
    String from;

    public ChatData()
    {}

    public String getText() {
        return message;
    }

    public void setText(String text) {
        this.message = text;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }
}
