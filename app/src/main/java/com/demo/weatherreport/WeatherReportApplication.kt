package com.demo.weatherreport

import android.app.Application
import com.demo.weatherreport.di.injector.dataInjector
import com.demo.weatherreport.di.injector.networkInjector
import com.demo.weatherreport.di.injector.repositoryInjector
import com.demo.weatherreport.di.injector.viewModelInjector
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class WeatherReportApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@WeatherReportApplication)
            modules(dataInjector, repositoryInjector, viewModelInjector, networkInjector)
        }
    }
}