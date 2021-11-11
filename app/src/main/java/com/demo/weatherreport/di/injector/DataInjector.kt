package com.demo.weatherreport.di.injector

import com.demo.weatherreport.data.local.PrefUtils
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val dataInjector = module {
    single { PrefUtils(androidContext()) }
}