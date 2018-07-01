package com.example.suleman.whatsapp;



public class chatTab {

    String name;
    String lastmessage;
    String time;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastmessage() {
        return lastmessage;
    }

    public void setLastmessage(String lastmessage) {
        this.lastmessage = lastmessage;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public chatTab()
    {
        name=null;
        lastmessage=null;
        time=null;

    }

    public chatTab(String name, String lastmessage, String time) {
        this.name = name;
        this.lastmessage = lastmessage;
        this.time = time;
    }
}
