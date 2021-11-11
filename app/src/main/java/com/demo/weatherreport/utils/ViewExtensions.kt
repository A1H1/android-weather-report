package com.demo.weatherreport.utils

import android.app.Activity
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

fun View.show() {
    visibility = View.VISIBLE
}

fun View.hide() {
    visibility = View.GONE
}

fun View.show(enable: Boolean) {
    visibility = if (enable) View.VISIBLE else View.GONE
}

fun View.invisible(enable: Boolean) {
    visibility = if (enable) View.INVISIBLE else View.VISIBLE
}

fun View.setBackgroundTint(color: String) {
    if (color.isNotEmpty())
        backgroundTintList = ColorStateList.valueOf(Color.parseColor(color))
}

fun View.setBackgroundTint(@ColorRes color: Int) {
    backgroundTintList = ContextCompat.getColorStateList(context, color)
}

fun ImageView.setTint(@ColorRes color: Int) {
    imageTintList = ContextCompat.getColorStateList(context, color)
}

fun TextView.setColor(@ColorRes color: Int) {
    setTextColor(ContextCompat.getColorStateList(context, color))
}

fun AlertDialog.show(enable: Boolean) {
    if (enable) show() else dismiss()
}

fun View.setDebounceClickListener(debounceTime: Long = 1000L, action: () -> Unit) {
    setOnClickListener {
        when {
            tag != null && (tag as Long) > System.currentTimeMillis() -> return@setOnClickListener
            else -> {
                tag = System.currentTimeMillis() + debounceTime
                action()
            }
        }
    }
}

fun <T> debounce(
    waitMs: Long = 300L,
    coroutineScope: CoroutineScope,
    destinationFunction: (T) -> Unit
): (T) -> Unit {
    var debounceJob: Job? = null
    return { param: T ->
        debounceJob?.cancel()
        debounceJob = coroutineScope.launch {
            delay(waitMs)
            destinationFunction(param)
        }
    }
}

fun View.setOnClickListener(action: () -> Unit) {
    setOnClickListener { action.invoke() }
}

fun Int.manipulateColor(factor: Float): Int {
    val a = Color.alpha(this)
    val r = (Color.red(this) * factor).roundToInt()
    val g = (Color.green(this) * factor).roundToInt()
    val b = (Color.blue(this) * factor).roundToInt()
    return Color.argb(
        a,
        r.coerceAtMost(255),
        g.coerceAtMost(255),
        b.coerceAtMost(255)
    )
}

fun ImageView.greyScale(saturation: Float = 0f) {
    val matrix = ColorMatrix().apply {
        setSaturation(saturation)
    }
    colorFilter = ColorMatrixColorFilter(matrix)
}

fun ImageView.resetScale() {
    colorFilter = null
}

fun Fragment.hideKeyboard() {
    view?.let { activity?.hideKeyboard(it) }
}

fun Activity.hideKeyboard() {
    hideKeyboard(currentFocus ?: View(this))
}

fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}