package com.demo.weatherreport.ui.dashboard.date

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.demo.weatherreport.data.model.event.DataType
import com.demo.weatherreport.data.model.event.DataWrapper
import com.demo.weatherreport.data.model.event.Event
import com.demo.weatherreport.data.model.event.ResponseDataType
import com.demo.weatherreport.data.model.weather.WeatherDTO
import com.demo.weatherreport.data.repository.UserRepository
import com.demo.weatherreport.data.repository.WeatherRepository
import com.demo.weatherreport.utils.AppConst
import com.demo.weatherreport.utils.AppUtils
import com.demo.weatherreport.utils.postData
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class DateViewModel(
    private val userRepository: UserRepository,
    private val weatherRepository: WeatherRepository
) : ViewModel() {
    val userName get() = userRepository.getUserName()
    val tempType get() = userRepository.getTempType()

    private val _weatherInfo = MutableLiveData<Event<DataWrapper<WeatherDTO>>>()
    val weatherInfo: LiveData<Event<DataWrapper<WeatherDTO>>>
        get() = _weatherInfo

    private var selectedCity = 0
    private var selectedDate = 0
    private val dates = mutableListOf<Int>()
    private val forecast = mutableListOf<WeatherDTO>()

    fun createDatesIndex() {
        val c = Calendar.getInstance()
        for (i in 1..8) {
            c.add(Calendar.DATE, 1)
            dates.add(c.get(Calendar.DAY_OF_MONTH))
        }
    }

    fun getForecast() {
        _weatherInfo.postData(DataWrapper(isLoading = true))
        viewModelScope.launch {
            val response = weatherRepository.getForecast(
                AppConst.citiesCoordinates[selectedCity].first(),
                AppConst.citiesCoordinates[selectedCity].last()
            )
            if (response.type == ResponseDataType.ERROR) {
                _weatherInfo.postData(DataWrapper(error = response.error, type = DataType.ERROR))
            } else {
                val sdf = SimpleDateFormat(AppConst.TIME_FORMAT, Locale.getDefault())
                val data = response.data!!
                forecast.clear()
                forecast.addAll(data.daily.map {
                    WeatherDTO(
                        tempMax = AppUtils.getTemperature(tempType, it.temp.max),
                        tempMin = AppUtils.getTemperature(tempType, it.temp.min),
                        temp = AppUtils.getTemperature(tempType, it.temp.day),
                        feelsLike = AppUtils.getTemperature(tempType, it.feelsLike.day),
                        humidity = it.humidity.toString(),
                        pressure = it.pressure.toString(),
                        sunrise = sdf.format(Date(it.sunrise * 1000)),
                        sunset = sdf.format(Date(it.sunset * 1000)),
                        icon = AppUtils.getIconUrl(it.weather.first().icon),
                        weatherType = it.weather.first().main,
                        speed = it.windSpeed.toString(),
                    )
                })
                setWeatherInfo()
            }
        }
    }

    fun onCitySelected(index: Int) {
        selectedCity = index
        getForecast()
    }

    fun onDateSelected(day: Int) {
        selectedDate = dates.indexOf(day)
        setWeatherInfo()
    }

    private fun setWeatherInfo() {
        _weatherInfo.postData(DataWrapper(data = forecast[selectedDate], type = DataType.SUCCESS))
    }
}