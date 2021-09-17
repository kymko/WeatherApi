package com.example.weatherapi.data;

import com.google.gson.annotations.SerializedName;

public class Example {

    @SerializedName("main")
    WeatherModel model;

    public WeatherModel getModel() {
        return model;
    }

    public void setModel(WeatherModel model) {
        this.model = model;
    }
}
