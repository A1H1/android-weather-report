package com.demo.weatherreport.ui.dashboard.date

import android.app.DatePickerDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.DatePicker
import com.demo.weatherreport.R
import com.demo.weatherreport.base.BaseMVVMFragment
import com.demo.weatherreport.data.model.event.DataType
import com.demo.weatherreport.data.model.weather.WeatherDTO
import com.demo.weatherreport.databinding.FragmentDateBinding
import com.demo.weatherreport.di.utils.EventObserver
import com.demo.weatherreport.utils.AppConst.DATE_FORMAT
import com.demo.weatherreport.utils.loadUrl
import com.demo.weatherreport.utils.setDebounceClickListener
import com.demo.weatherreport.utils.show
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.*

class DateFragment : BaseMVVMFragment<DateViewModel, FragmentDateBinding>(),
    DatePickerDialog.OnDateSetListener, AdapterView.OnItemSelectedListener {
    override fun inflateViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentDateBinding.inflate(inflater, container, false)

    override val viewModel: DateViewModel by viewModel()

    override fun init() {
        super.init()
        updateUI()
        viewModel.createDatesIndex()
        viewModel.getForecast()
    }

    override fun setOnClickListeners() {
        super.setOnClickListeners()
        binding.dateTextView.setDebounceClickListener(action = ::selectDate)
        binding.calendarImageView.setDebounceClickListener(action = ::selectDate)
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

    override fun onDateSet(view: DatePicker, year: Int, month: Int, day: Int) {
        binding.dateTextView.text = getString(R.string.date_format, day, month + 1, year)
        viewModel.onDateSelected(day)
    }

    override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
        viewModel.onCitySelected(pos)
    }

    override fun onNothingSelected(parent: AdapterView<*>) {
    }

    private fun selectDate() {
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)
        val dialog = DatePickerDialog(requireContext(), this, year, month, day)
        c.add(Calendar.DATE, 1)
        dialog.datePicker.minDate = c.timeInMillis
        c.add(Calendar.DATE, 7)
        dialog.datePicker.maxDate = c.timeInMillis
        dialog.show()
    }

    private fun updateUI() {
        val df = SimpleDateFormat(DATE_FORMAT, Locale.getDefault())
        val c = Calendar.getInstance()
        c.add(Calendar.DATE, 1)
        binding.nameTextView.text = getString(R.string.greet_user, viewModel.userName)
        binding.dateTextView.text = df.format(c.time)

        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.cities,
            R.layout.spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(R.layout.spinner_dropdown_item)
            binding.citySpinner.adapter = adapter
        }
        binding.citySpinner.onItemSelectedListener = this
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