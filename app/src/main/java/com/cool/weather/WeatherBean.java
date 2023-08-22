package com.cool.weather;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.json.JSONObject;

public class WeatherBean {

    private int id;
    private String main;
    private String description;
    private double temp; //℉

    private String icon;

    public static WeatherBean parseBaseInfoFromJSONObject(JSONObject json) {
        WeatherBean bean = new WeatherBean();
        if (json != null) {
            bean.setId(json.optInt("id"));
            bean.setMain(json.optString("main"));
            bean.setDescription(json.optString("description"));
            bean.setIcon(json.optString("icon"));
        }
        return bean;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMain() {
        return main;
    }

    public void setMain(String main) {
        this.main = main;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getTemp() {
        return temp;
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    @NonNull
    @Override
    public String toString() {
        return main + ":" + description + ", " + temp + "℉";
    }

}