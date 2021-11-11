package com.demo.weatherreport.ui.dashboard.settings

import androidx.lifecycle.ViewModel
import com.demo.weatherreport.data.repository.UserRepository

class SettingsViewModel(private val userRepository: UserRepository) : ViewModel() {
    val userName get() = userRepository.getUserName()
    val city get() = userRepository.getUserCity()
    var isCelsius = userRepository.getTempType() == "C"

    fun onToggleChanged(isChecked: Boolean) {
        isCelsius = isChecked
        userRepository.storeTempType(if (isChecked) "C" else "F")
    }
}