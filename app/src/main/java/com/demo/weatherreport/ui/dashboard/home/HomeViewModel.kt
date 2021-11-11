package com.demo.weatherreport.ui.dashboard.home

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
import com.demo.weatherreport.utils.AppConst.TIME_FORMAT
import com.demo.weatherreport.utils.AppUtils
import com.demo.weatherreport.utils.postData
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class HomeViewModel(
    private val userRepository: UserRepository,
    private val weatherRepository: WeatherRepository
) : ViewModel() {
    val userName get() = userRepository.getUserName()
    val city get() = userRepository.getUserCity()
    val tempType get() = userRepository.getTempType()

    private val _weatherInfo = MutableLiveData<Event<DataWrapper<WeatherDTO>>>()
    val weatherInfo: LiveData<Event<DataWrapper<WeatherDTO>>>
        get() = _weatherInfo

    fun getCurrentCityWeather() {
        _weatherInfo.postData(DataWrapper(isLoading = true))
        viewModelScope.launch {
            val response = weatherRepository.getCurrentCityWeather()
            if (response.type == ResponseDataType.ERROR) {
                _weatherInfo.postData(DataWrapper(error = response.error, type = DataType.ERROR))
            } else {
                val data = response.data!!
                val sdf = SimpleDateFormat(TIME_FORMAT, Locale.getDefault())
                _weatherInfo.postData(
                    DataWrapper(
                        data = WeatherDTO(
                            tempMax = AppUtils.getTemperature(tempType, data.main.tempMax),
                            tempMin = AppUtils.getTemperature(tempType, data.main.tempMin),
                            temp = AppUtils.getTemperature(tempType, data.main.temp),
                            feelsLike = AppUtils.getTemperature(tempType, data.main.feelsLike),
                            humidity = data.main.humidity.toString(),
                            pressure = data.main.pressure.toString(),
                            sunrise = sdf.format(Date(data.sys.sunrise * 1000)),
                            sunset = sdf.format(Date(data.sys.sunset * 1000)),
                            icon = AppUtils.getIconUrl(data.weather.first().icon),
                            weatherType = data.weather.first().main,
                            speed = data.wind.speed.toString(),
                        ), type = DataType.SUCCESS
                    )
                )
            }
        }
    }
}