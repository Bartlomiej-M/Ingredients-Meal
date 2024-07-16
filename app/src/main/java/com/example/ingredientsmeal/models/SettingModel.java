package com.example.ingredientsmeal.models;

public class SettingModel {
    String hightUser, weightUser, bmp;

    //a blank constructor//
    public SettingModel() {

    }

    public SettingModel(String hightUser, String weightUser, String bmp) {
        this.hightUser = hightUser;
        this.weightUser = weightUser;
        this.bmp = bmp;
    }

    public String getHightUser() {
        return hightUser;
    }

    public void setHightUser(String hightUser) {
        this.hightUser = hightUser;
    }

    public String getWeightUser() {
        return weightUser;
    }

    public void setWeightUser(String weightUser) {
        this.weightUser = weightUser;
    }

    public String getBmp() {
        return bmp;
    }

    public void setBmp(String bmp) {
        this.bmp = bmp;
    }
}