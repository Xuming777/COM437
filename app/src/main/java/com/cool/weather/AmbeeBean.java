package com.cool.weather;

import androidx.annotation.NonNull;

public class AmbeeBean {
    private String pollutant;
    private String concentration;
    private String category;
    private String AQI;

    public String getPollutant() {
        return pollutant;
    }

    public void setPollutant(String pollutant) {
        this.pollutant = pollutant;
    }

    public String getConcentration() {
        return concentration;
    }

    public void setConcentration(String concentration) {
        this.concentration = concentration;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getAQI() {
        return AQI;
    }

    public void setAQI(String AQI) {
        this.AQI = AQI;
    }

    @NonNull
    @Override
    public String toString() {
        return "pollutant:" + pollutant + "\n" +
                "concentration:" + concentration + "\n" +
                "category:" + category + "\n" +
                "AQI:" + AQI + "\n";
    }

}