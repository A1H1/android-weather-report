package com.demo.weatherreport.ui.dashboard.days

import android.view.LayoutInflater
import android.view.ViewGroup
import com.demo.weatherreport.R
import com.demo.weatherreport.base.BaseAdapter
import com.demo.weatherreport.base.BaseViewHolder
import com.demo.weatherreport.data.model.weather.WeatherDTO
import com.demo.weatherreport.databinding.ItemWeatherDayBinding
import com.demo.weatherreport.utils.loadUrl
import kotlinx.coroutines.CoroutineScope

class DaysAdapter(coroutineScope: CoroutineScope) :
    BaseAdapter<WeatherDTO, ItemWeatherDayBinding>(coroutineScope) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ItemWeatherDayBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    inner class ViewHolder(private val binding: ItemWeatherDayBinding) :
        BaseViewHolder<WeatherDTO, ItemWeatherDayBinding>(binding) {

        override fun bind(data: WeatherDTO) {
            binding.dateTextView.text = data.date
            binding.typeTextView.text = data.weatherType
            binding.weatherImageView.loadUrl(data.icon)
            binding.maxTextView.text =
                binding.maxTextView.context.getString(R.string.temp, data.tempMax)
            binding.minTextView.text =
                binding.maxTextView.context.getString(R.string.temp, data.tempMin)
        }
    }
}