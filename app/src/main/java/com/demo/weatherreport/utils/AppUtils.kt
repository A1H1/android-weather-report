package com.demo.weatherreport.utils

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import android.util.Base64
import android.view.View
import android.view.inputmethod.InputMethodManager
import java.io.File
import java.io.FileOutputStream
import java.io.UnsupportedEncodingException
import java.text.DecimalFormat


object AppUtils {
    fun addCleanIntent() = Intent.FLAG_ACTIVITY_NEW_TASK or
            Intent.FLAG_ACTIVITY_CLEAR_TOP or
            Intent.FLAG_ACTIVITY_SINGLE_TOP or
            Intent.FLAG_ACTIVITY_CLEAR_TASK

    fun hideKeyboardFromView(context: Context, view: View) {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    fun showKeyboard(context: Context, view: View?) {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(view, InputMethodManager.SHOW_FORCED)
    }

    fun getUriFromRowFile(context: Context, id: Int): Uri =
        Uri.parse("android.resource://" + context.packageName + "/" + id)

    @Throws(Exception::class)
    fun decodedJWT(JWTEncoded: String): String {
        val split = JWTEncoded.split(".").toTypedArray()
        return getJson(split[1])
    }

    @Throws(UnsupportedEncodingException::class)
    private fun getJson(strEncoded: String): String {
        val decodedBytes: ByteArray = Base64.decode(strEncoded, Base64.URL_SAFE)
        return String(decodedBytes)
    }

    fun saveByte(bytes: ByteArray, file: File) {
        val outputStream = FileOutputStream(file)
        outputStream.write(bytes)
        outputStream.close()
    }

    fun dpToPx(context: Context, dp: Int) =
        context.resources.displayMetrics.density * dp

    fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val nw = connectivityManager.activeNetwork ?: return false
        val actNw = connectivityManager.getNetworkCapabilities(nw) ?: return false
        return when {
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH) -> true
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_VPN) -> true
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_LOWPAN) -> true
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI_AWARE) -> true
            else -> false
        }
    }

    fun getTemperature(type: String, temp: Double): String {
        val df = DecimalFormat("#.##")
        return if (type == "C") df.format(temp - 273.15)
        else df.format((temp - 273.15) * (9 / 5) + 32)
    }

    fun getIconUrl(icon: String) = "https://openweathermap.org/img/wn/$icon@2x.png"
}
