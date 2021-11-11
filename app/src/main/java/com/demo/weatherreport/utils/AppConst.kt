package com.demo.weatherreport.utils

import com.demo.weatherreport.BuildConfig

object AppConst {
    const val PREF_FILE = BuildConfig.APPLICATION_ID
    const val PREF_USER_NAME = "user_name"
    const val PREF_USER_CITY = "user_city"
    const val PREF_USER_LAT= "user_lat"
    const val PREF_USER_LNG = "user_lng"
    const val PREF_TEMP_TYPE = "temp_type"

    const val BASE_URL = "https://api.openweathermap.org/data/2.5/"
    const val API_KEY = "d0f46ca6a730bb2642a76f21bd7b24e1"

    const val ERROR_401 = "401"
    const val ERROR_422 = "422"
    const val ERROR_403 = "403"
    const val ERROR_404 = "404"

    const val SERVER_TIMEOUT_CONNECT = 18L
    const val SERVER_TIMEOUT_READ = 60L
    const val SERVER_TIMEOUT_WRITE = 60L

    const val DATE_FORMAT = "dd/MM/yyyy"
    const val TIME_FORMAT = "hh:mm a"

    val citiesCoordinates =
        listOf(listOf(28.7041, 77.1025), listOf(19.0760, 72.8777), listOf(28.5355, 77.3910))
}