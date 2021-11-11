package com.demo.weatherreport.data.api

import com.demo.weatherreport.data.model.weather.ForecastResponse
import com.demo.weatherreport.data.model.weather.WeatherResponse
import com.demo.weatherreport.utils.AppConst
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {
    @GET("weather")
    suspend fun getWeather(
        @Query("q") city: String,
        @Query("appid") apiKey: String = AppConst.API_KEY
    ): WeatherResponse

    @GET("onecall")
    suspend fun getForecast(
        @Query("lat") lat: Double,
        @Query("lon") lng: Double,
        @Query("exclude") exclude: String = "hourly,minutely",
        @Query("appid") apiKey: String = AppConst.API_KEY
    ): ForecastResponse
}