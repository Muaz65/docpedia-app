package com.example.suleman.whatsapp;

import android.support.annotation.DrawableRes;


public class Contact implements java.io.Serializable{
    String name;
    int image;
    String number;
    boolean selected = false;
    private String cid;

    public void setSelected(boolean s){ selected = s; }
    public boolean getSelected(){ return selected; }

    public String  getNumber(){
        return number;
    }
    public void setnumber( String num){
        this.number = num;
    }

    public int getImage(){
        return image;
    }
    public void setImage(@DrawableRes int img){
        image = img;
    }


    public String getName(){
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }
}
