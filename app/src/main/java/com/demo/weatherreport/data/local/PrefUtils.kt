package com.demo.weatherreport.data.local

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.demo.weatherreport.utils.AppConst.PREF_FILE
import com.demo.weatherreport.utils.AppConst.PREF_TEMP_TYPE
import com.demo.weatherreport.utils.AppConst.PREF_USER_CITY
import com.demo.weatherreport.utils.AppConst.PREF_USER_LAT
import com.demo.weatherreport.utils.AppConst.PREF_USER_LNG
import com.demo.weatherreport.utils.AppConst.PREF_USER_NAME

class PrefUtils(context: Context) {
    private var pref: SharedPreferences = context.getSharedPreferences(PREF_FILE, MODE_PRIVATE)

    fun setUserName(name: String) {
        val pref = this.pref.edit()
        pref.putString(PREF_USER_NAME, name)
        pref.apply()
    }

    fun getUserName() = pref.getString(PREF_USER_NAME, "").orEmpty()

    fun setUserCity(city: String) {
        val pref = this.pref.edit()
        pref.putString(PREF_USER_CITY, city)
        pref.apply()
    }

    fun getUserCity() = pref.getString(PREF_USER_CITY, "").orEmpty()

    fun setTempType(type: String) {
        val pref = this.pref.edit()
        pref.putString(PREF_TEMP_TYPE, type)
        pref.apply()
    }

    fun getTempType() = pref.getString(PREF_TEMP_TYPE, "C").orEmpty()

    fun setUserLatLng(latLng: List<Double>) {
        val pref = this.pref.edit()
        pref.putFloat(PREF_USER_LAT, latLng.first().toFloat())
        pref.putFloat(PREF_USER_LNG, latLng.last().toFloat())
        pref.apply()
    }

    fun getUserLatLng() = listOf(
        pref.getFloat(PREF_USER_LAT, 0F).toDouble(),
        pref.getFloat(PREF_USER_LNG, 0F).toDouble()
    )

    fun clearAll() {
        val pref = this.pref.edit()
        pref.clear()
        pref.apply()
    }
}