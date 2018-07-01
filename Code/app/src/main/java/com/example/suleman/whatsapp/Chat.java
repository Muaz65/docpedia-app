package com.example.suleman.whatsapp;


public class Chat implements java.io.Serializable{

    private String mName;
    private String mLastChat;
    private String mTime;
    private int mImage;
    private boolean online;
    private String chatId;


    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getLastChat() {
        return mLastChat;
    }

    public void setLastChat(String lastChat) {
        mLastChat = lastChat;
    }

    public String getTime() {
        return mTime;
    }

    public void setmTime(String time) {
        mTime = time;
    }

    public int getImage() {
        return mImage;
    }

    public void setImage(int image) {
        mImage = image;
    }

    public boolean getOnline(){
        return online;
    }

    public void setOnline(boolean on){
        online = on;
    }

    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }
}