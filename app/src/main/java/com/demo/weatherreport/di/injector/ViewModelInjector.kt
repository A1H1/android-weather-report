package com.demo.weatherreport.di.injector

import com.demo.weatherreport.ui.dashboard.date.DateViewModel
import com.demo.weatherreport.ui.dashboard.days.DaysViewModel
import com.demo.weatherreport.ui.dashboard.home.HomeViewModel
import com.demo.weatherreport.ui.dashboard.settings.SettingsViewModel
import com.demo.weatherreport.ui.login.LoginViewModel
import com.demo.weatherreport.ui.splash.SplashViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelInjector = module {
    viewModel { SplashViewModel(get()) }
    viewModel { LoginViewModel(get()) }
    viewModel { HomeViewModel(get(), get()) }
    viewModel { DateViewModel(get(), get()) }
    viewModel { DaysViewModel(get(), get()) }
    viewModel { SettingsViewModel(get()) }
}