package com.example.ingredientsmeal.models;

public class AddModel {

    public String duration, portion, level, energy;

    public AddModel(String duration, String portion, String level, String energy) {
        this.duration = duration;
        this.portion = portion;
        this.level = level;
        this.energy = energy;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getPortion() {
        return portion;
    }

    public void setPortion(String portion) {
        this.portion = portion;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getEnergy() {
        return energy;
    }

    public void setEnergy(String energy) {
        this.energy = energy;
    }
}