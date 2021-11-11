package com.demo.weatherreport.ui.dashboard.days

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

class DaysViewModel(
    private val userRepository: UserRepository,
    private val weatherRepository: WeatherRepository
) : ViewModel() {
    val userName get() = userRepository.getUserName()
    val tempType get() = userRepository.getTempType()
    val city get() = userRepository.getUserCity()

    private val _weatherInfo = MutableLiveData<Event<DataWrapper<List<WeatherDTO>>>>()
    val weatherInfo: LiveData<Event<DataWrapper<List<WeatherDTO>>>>
        get() = _weatherInfo

    fun getForecast() {
        _weatherInfo.postData(DataWrapper(isLoading = true))
        viewModelScope.launch {
            val latLng = userRepository.getUserLatLng()
            val response = weatherRepository.getForecast(latLng.first(), latLng.last())
            if (response.type == ResponseDataType.ERROR) {
                _weatherInfo.postData(DataWrapper(error = response.error, type = DataType.ERROR))
            } else {
                val sdf = SimpleDateFormat(AppConst.TIME_FORMAT, Locale.getDefault())
                val dateFormat = SimpleDateFormat(AppConst.DATE_FORMAT, Locale.getDefault())
                _weatherInfo.postData(
                    DataWrapper(
                        data = response.data!!.daily.map {
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
                                date = dateFormat.format(Date(it.dt * 1000))
                            )
                        }, type = DataType.SUCCESS
                    )
                )
            }
        }
    }
}