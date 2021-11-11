package com.demo.weatherreport.di.utils

import com.squareup.moshi.Moshi

fun provideMoshi(): Moshi = Moshi.Builder().build()