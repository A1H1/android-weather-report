package com.demo.weatherreport.ui.login

import android.location.Address
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.demo.weatherreport.data.model.event.Event
import com.demo.weatherreport.data.repository.UserRepository
import com.demo.weatherreport.utils.postEvent

class LoginViewModel(private val repository: UserRepository) : ViewModel() {
    private var isLocationFound = false
    private val _loginStatus = MutableLiveData<Event<Boolean>>()
    val loginStatus: LiveData<Event<Boolean>> get() = _loginStatus

    fun login(name: String) {
        repository.storeUserName(name)
        _loginStatus.postEvent(isLocationFound)
    }

    fun onLocationFound(address: Address) {
        repository.storeUserCity(address.locality)
        repository.storeUserLatLng(listOf(address.latitude, address.longitude))
        isLocationFound = true
    }
}
