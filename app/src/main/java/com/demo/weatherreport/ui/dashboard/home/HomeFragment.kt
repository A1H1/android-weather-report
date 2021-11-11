package com.demo.weatherreport.ui.dashboard.home

import android.view.LayoutInflater
import android.view.ViewGroup
import com.demo.weatherreport.R
import com.demo.weatherreport.base.BaseMVVMFragment
import com.demo.weatherreport.data.model.event.DataType
import com.demo.weatherreport.data.model.weather.WeatherDTO
import com.demo.weatherreport.databinding.FragmentHomeBinding
import com.demo.weatherreport.di.utils.EventObserver
import com.demo.weatherreport.utils.loadUrl
import com.demo.weatherreport.utils.show
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : BaseMVVMFragment<HomeViewModel, FragmentHomeBinding>() {
    override fun inflateViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentHomeBinding.inflate(inflater, container, false)

    override val viewModel: HomeViewModel by viewModel()

    override fun init() {
        super.init()
        viewModel.getCurrentCityWeather()
        updateUI()
    }

    override fun observers() {
        viewModel.weatherInfo.observe(this, EventObserver {
            showLoading(it.isLoading)
            when (it.type) {
                DataType.SUCCESS -> updateWeatherUI(it.data!!)
                DataType.ERROR -> showError(it.error!!)
            }
        })
    }

    override fun showLoading(isLoading: Boolean) {
        binding.progressBarGroup.show(isLoading)
    }

    private fun updateUI() {
        binding.nameTextView.text = getString(R.string.greet_user, viewModel.userName)
        binding.cityTextView.text = viewModel.city
    }

    private fun updateWeatherUI(data: WeatherDTO) {
        binding.minMaxTextView.text = getString(R.string.min_max_temp, data.tempMax, data.tempMin)
        binding.currentTempTextView.text = data.temp
        binding.currentTempTypeTextView.text = getString(R.string.temp_type, viewModel.tempType)
        binding.feelsLikeTextView.text = getString(R.string.feels_like, data.feelsLike)
        binding.weatherImageView.loadUrl(data.icon)
        binding.weatherTypeTextView.text = data.weatherType
        binding.windTextView.text = getString(R.string.wind_speed, data.speed)
        binding.pressureHumidityTextView.text =
            getString(R.string.pressure_humidity, data.pressure, data.humidity)
        binding.sunriseSunsetTextView.text =
            getString(R.string.sunrise_sunset, data.sunrise, data.sunset)
    }
}