package com.example.ingredientsmeal.models;

public class HistoryModel {

    public String historyTypeMeal, historyPreferenceMeal, historyCurrentDate, historyCurrentTime;

    public HistoryModel() {
    }

    public HistoryModel(String historyTypeMeal, String historyPreferenceMeal, String historyCurrentDate, String historyCurrentTime) {
        this.historyTypeMeal = historyTypeMeal;
        this.historyPreferenceMeal = historyPreferenceMeal;
        this.historyCurrentDate = historyCurrentDate;
        this.historyCurrentTime = historyCurrentTime;
    }

    public String getHistoryTypeMeal() {
        return historyTypeMeal;
    }

    public void setHistoryTypeMeal(String historyTypeMeal) {
        this.historyTypeMeal = historyTypeMeal;
    }

    public String getHistoryPreferenceMeal() {
        return historyPreferenceMeal;
    }

    public void setHistoryPreferenceMeal(String historyPreferenceMeal) {
        this.historyPreferenceMeal = historyPreferenceMeal;
    }

    public String getHistoryCurrentDate() {
        return historyCurrentDate;
    }

    public void setHistoryCurrentDate(String historyCurrentDate) {
        this.historyCurrentDate = historyCurrentDate;
    }

    public String getHistoryCurrentTime() {
        return historyCurrentTime;
    }

    public void setHistoryCurrentTime(String historyCurrentTime) {
        this.historyCurrentTime = historyCurrentTime;
    }

}
