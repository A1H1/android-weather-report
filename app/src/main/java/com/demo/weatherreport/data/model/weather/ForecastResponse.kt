package com.demo.weatherreport.data.model.weather


import com.squareup.moshi.Json

data class ForecastResponse(
    @field:Json(name = "current")
    val current: Current = Current(),
    @field:Json(name = "daily")
    val daily: List<Daily> = listOf(),
    @field:Json(name = "lat")
    val lat: Double = 0.0,
    @field:Json(name = "lon")
    val lon: Double = 0.0,
    @field:Json(name = "timezone")
    val timezone: String = "",
    @field:Json(name = "timezone_offset")
    val timezoneOffset: Int = 0
)

data class Current(
    @field:Json(name = "clouds")
    val clouds: Int = 0,
    @field:Json(name = "dew_point")
    val dewPoint: Double = 0.0,
    @field:Json(name = "dt")
    val dt: Int = 0,
    @field:Json(name = "feels_like")
    val feelsLike: Double = 0.0,
    @field:Json(name = "humidity")
    val humidity: Int = 0,
    @field:Json(name = "pressure")
    val pressure: Int = 0,
    @field:Json(name = "sunrise")
    val sunrise: Int = 0,
    @field:Json(name = "sunset")
    val sunset: Int = 0,
    @field:Json(name = "temp")
    val temp: Double = 0.0,
    @field:Json(name = "uvi")
    val uvi: Double = 0.0,
    @field:Json(name = "visibility")
    val visibility: Int = 0,
    @field:Json(name = "weather")
    val weather: List<Weather> = listOf(),
    @field:Json(name = "wind_deg")
    val windDeg: Int = 0,
    @field:Json(name = "wind_speed")
    val windSpeed: Double = 0.0
)

data class Daily(
    @field:Json(name = "clouds")
    val clouds: Int = 0,
    @field:Json(name = "dew_point")
    val dewPoint: Double = 0.0,
    @field:Json(name = "dt")
    val dt: Long = 0,
    @field:Json(name = "feels_like")
    val feelsLike: FeelsLike = FeelsLike(),
    @field:Json(name = "humidity")
    val humidity: Int = 0,
    @field:Json(name = "moon_phase")
    val moonPhase: Double = 0.0,
    @field:Json(name = "moonrise")
    val moonrise: Int = 0,
    @field:Json(name = "moonset")
    val moonset: Int = 0,
    @field:Json(name = "pop")
    val pop: Double = 0.0,
    @field:Json(name = "pressure")
    val pressure: Int = 0,
    @field:Json(name = "sunrise")
    val sunrise: Long = 0,
    @field:Json(name = "sunset")
    val sunset: Long = 0,
    @field:Json(name = "temp")
    val temp: Temp = Temp(),
    @field:Json(name = "uvi")
    val uvi: Double = 0.0,
    @field:Json(name = "weather")
    val weather: List<Weather> = listOf(),
    @field:Json(name = "wind_deg")
    val windDeg: Int = 0,
    @field:Json(name = "wind_gust")
    val windGust: Double = 0.0,
    @field:Json(name = "wind_speed")
    val windSpeed: Double = 0.0
)

data class FeelsLike(
    @field:Json(name = "day")
    val day: Double = 0.0,
    @field:Json(name = "eve")
    val eve: Double = 0.0,
    @field:Json(name = "morn")
    val morn: Double = 0.0,
    @field:Json(name = "night")
    val night: Double = 0.0
)

data class Temp(
    @field:Json(name = "day")
    val day: Double = 0.0,
    @field:Json(name = "eve")
    val eve: Double = 0.0,
    @field:Json(name = "max")
    val max: Double = 0.0,
    @field:Json(name = "min")
    val min: Double = 0.0,
    @field:Json(name = "morn")
    val morn: Double = 0.0,
    @field:Json(name = "night")
    val night: Double = 0.0
)