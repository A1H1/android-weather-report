package com.demo.weatherreport.di.injector

import com.demo.weatherreport.di.utils.provideMoshi
import com.demo.weatherreport.network.NetworkModule.provideClient
import com.demo.weatherreport.network.NetworkModule.provideMoshiFactory
import com.demo.weatherreport.network.NetworkModule.provideWeatherService
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val networkInjector = module {
    single { provideMoshiFactory() }
    single { provideClient(get(), androidContext()) }
    single { provideWeatherService(get()) }
    single { provideMoshi() }
}