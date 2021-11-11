package com.demo.weatherreport.utils

import android.annotation.SuppressLint
import android.net.Uri
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import java.io.File

@SuppressLint("CheckResult")
fun ImageView.loadUrl(
    url: String,
    isRounded: Boolean = false,
    placeholder: Int? = null,
    radius: Int? = null
) {
    Glide.with(this).load(url).apply {
        if (isRounded) circleCrop()
        placeholder?.let { placeholder(placeholder) }
        radius?.let { transform(CenterCrop(), RoundedCorners(radius)) }
    }.into(this)
}

@SuppressLint("CheckResult")
fun ImageView.loadUri(uri: Uri, isRounded: Boolean = false) {
    Glide.with(this).load(uri).apply {
        if (isRounded) circleCrop()
    }.into(this)
}

@SuppressLint("CheckResult")
fun ImageView.loadFile(file: File, isRounded: Boolean = false, placeholder: Int? = null) {
    Glide.with(this).load(file).apply {
        if (isRounded) circleCrop()
        placeholder?.let { placeholder(placeholder) }
    }.into(this)
}

fun ImageView.loadVector(resourceId: Int) {
    Glide.with(this).load(resourceId).into(this)
}

@SuppressLint("CheckResult")
fun ImageView.loadRoundedUrl(
    url: String,
    isRounded: Boolean = false,
    placeholder: Int? = null,
    radius: Int
) {
    Glide.with(this).load(url).apply {
        if (isRounded) circleCrop()
        placeholder?.let { placeholder(placeholder) }
        transform(RoundedCorners(radius))
    }.into(this)
}