package com.example.weatherapi.internet;

import com.example.weatherapi.data.Example;
import com.example.weatherapi.data.WeatherModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherApi {

    @GET("/data/2.5/weather")
    Call<Example> getCurrentWeather(
            @Query("q") String name,
            @Query("appid") String key);

    @GET("/data/2.5/weather?&appid=b211aae5545e8d3de75404d096930c95")
    Call<WeatherModel> getWeather(
            @Query("q") String name);



}
