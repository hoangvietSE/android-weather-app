package com.example.weatherapp.model;

public class SevenNextDay {
    private String dayOfWeek;
    private String date;
    private String status;
    private String icon;
    private String maxTemp;
    private String minTemp;

    public SevenNextDay(String dayOfWeek, String date, String status, String icon, String maxTemp, String minTemp) {
        this.dayOfWeek = dayOfWeek;
        this.date = date;
        this.status = status;
        this.icon = icon;
        this.maxTemp = maxTemp;
        this.minTemp = minTemp;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getMaxTemp() {
        return maxTemp;
    }

    public void setMaxTemp(String maxTemp) {
        this.maxTemp = maxTemp;
    }

    public String getMinTemp() {
        return minTemp;
    }

    public void setMinTemp(String minTemp) {
        this.minTemp = minTemp;
    }
}
