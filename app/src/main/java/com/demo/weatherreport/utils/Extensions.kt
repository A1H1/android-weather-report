package com.demo.weatherreport.utils

import androidx.lifecycle.MutableLiveData
import com.demo.weatherreport.data.model.event.DataWrapper
import com.demo.weatherreport.data.model.event.Event

fun <T> MutableLiveData<Event<DataWrapper<T>>>.postData(dataWrapper: DataWrapper<T>) {
    postValue(Event(dataWrapper))
}

fun <T> MutableLiveData<Event<T>>.postEvent(data: T) {
    postValue(Event(data))
}