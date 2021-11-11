package com.demo.weatherreport.data.repository

import com.demo.weatherreport.base.ErrorHandler
import com.demo.weatherreport.data.api.WeatherService
import com.demo.weatherreport.data.local.PrefUtils
import com.demo.weatherreport.data.model.event.ResponseDataType
import com.demo.weatherreport.data.model.event.ResponseWrapper
import com.demo.weatherreport.data.model.weather.ForecastResponse
import com.demo.weatherreport.data.model.weather.WeatherResponse

interface WeatherRepository {
    suspend fun getCurrentCityWeather(): ResponseWrapper<WeatherResponse>
    suspend fun getForecast(lat: Double, lng: Double): ResponseWrapper<ForecastResponse>
}

class WeatherRepositoryImpl(private val prefUtils: PrefUtils, private val service: WeatherService) :
    WeatherRepository {
    override suspend fun getCurrentCityWeather(): ResponseWrapper<WeatherResponse> {
        return try {
            ResponseWrapper(
                data = service.getWeather(prefUtils.getUserCity()),
                type = ResponseDataType.SUCCESS
            )
        } catch (e: Exception) {
            ResponseWrapper(error = ErrorHandler.parseError(e), type = ResponseDataType.ERROR)
        }
    }

    override suspend fun getForecast(lat: Double, lng: Double): ResponseWrapper<ForecastResponse> {
        return try {
            ResponseWrapper(
                data = service.getForecast(lat, lng),
                type = ResponseDataType.SUCCESS
            )
        } catch (e: Exception) {
            ResponseWrapper(error = ErrorHandler.parseError(e), type = ResponseDataType.ERROR)
        }
    }
}