package com.demo.weatherreport.data.model.weather

data class WeatherDTO(
    val tempMax: String,
    val tempMin: String,
    val temp: String,
    val feelsLike: String,
    val humidity: String,
    val pressure: String,
    val sunrise: String,
    val sunset: String,
    val icon: String,
    val weatherType: String,
    val speed: String,
    val date: String = "",
)
