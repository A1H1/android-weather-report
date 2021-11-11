package com.demo.weatherreport.ui.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.demo.weatherreport.data.model.event.Event
import com.demo.weatherreport.data.repository.UserRepository
import com.demo.weatherreport.utils.postEvent
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashViewModel(private val repository: UserRepository) : ViewModel() {
    private val _userStatus = MutableLiveData<Event<Boolean>>()
    val userStatus: LiveData<Event<Boolean>> get() = _userStatus

    fun checkUserStatus() {
        viewModelScope.launch {
            delay(2000)
            _userStatus.postEvent(repository.getUserName().isEmpty())
        }
    }
}
