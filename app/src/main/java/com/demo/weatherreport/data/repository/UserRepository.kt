package com.demo.weatherreport.data.repository

import com.demo.weatherreport.data.local.PrefUtils

interface UserRepository {
    fun getUserName(): String
    fun getUserCity(): String
    fun getTempType(): String
    fun getUserLatLng(): List<Double>

    fun storeUserName(name: String)
    fun storeUserCity(city: String)
    fun storeTempType(tempType: String)
    fun storeUserLatLng(latLng: List<Double>)
}

class UserRepositoryImpl(private val prefUtils: PrefUtils) : UserRepository {
    override fun getUserName() = prefUtils.getUserName()

    override fun getUserCity() = prefUtils.getUserCity()

    override fun getTempType() = prefUtils.getTempType()
    override fun getUserLatLng() = prefUtils.getUserLatLng()

    override fun storeUserName(name: String) {
        prefUtils.setUserName(name)
    }

    override fun storeUserCity(city: String) {
        prefUtils.setUserCity(city)
    }

    override fun storeTempType(tempType: String) {
        prefUtils.setTempType(tempType)
    }

    override fun storeUserLatLng(latLng: List<Double>) {
        prefUtils.setUserLatLng(latLng)
    }
}