package com.demo.weatherreport.data.model.weather


import com.squareup.moshi.Json
import se.ansman.kotshi.JsonSerializable

@JsonSerializable
data class WeatherResponse(
    @field:Json(name = "base")
    val base: String = "",
    @field:Json(name = "clouds")
    val clouds: Clouds = Clouds(),
    @field:Json(name = "cod")
    val cod: Int = 0,
    @field:Json(name = "coord")
    val coord: Coord = Coord(),
    @field:Json(name = "dt")
    val dt: Int = 0,
    @field:Json(name = "id")
    val id: Int = 0,
    @field:Json(name = "main")
    val main: Main = Main(),
    @field:Json(name = "name")
    val name: String = "",
    @field:Json(name = "sys")
    val sys: Sys = Sys(),
    @field:Json(name = "timezone")
    val timezone: Int = 0,
    @field:Json(name = "visibility")
    val visibility: Int = 0,
    @field:Json(name = "weather")
    val weather: List<Weather> = listOf(),
    @field:Json(name = "wind")
    val wind: Wind = Wind()
)

data class Clouds(
    @field:Json(name = "all")
    val all: Int = 0
)

data class Coord(
    @field:Json(name = "lat")
    val lat: Double = 0.0,
    @field:Json(name = "lon")
    val lon: Double = 0.0
)

data class Main(
    @field:Json(name = "feels_like")
    val feelsLike: Double = 0.0,
    @field:Json(name = "humidity")
    val humidity: Int = 0,
    @field:Json(name = "pressure")
    val pressure: Int = 0,
    @field:Json(name = "temp")
    val temp: Double = 0.0,
    @field:Json(name = "temp_max")
    val tempMax: Double = 0.0,
    @field:Json(name = "temp_min")
    val tempMin: Double = 0.0
)

data class Sys(
    @field:Json(name = "country")
    val country: String = "",
    @field:Json(name = "id")
    val id: Int = 0,
    @field:Json(name = "sunrise")
    val sunrise: Long = 0,
    @field:Json(name = "sunset")
    val sunset: Long = 0,
    @field:Json(name = "type")
    val type: Int = 0
)

data class Weather(
    @field:Json(name = "description")
    val description: String = "",
    @field:Json(name = "icon")
    val icon: String = "",
    @field:Json(name = "id")
    val id: Int = 0,
    @field:Json(name = "main")
    val main: String = ""
)

data class Wind(
    @field:Json(name = "deg")
    val deg: Int = 0,
    @field:Json(name = "speed")
    val speed: Double = 0.0
)