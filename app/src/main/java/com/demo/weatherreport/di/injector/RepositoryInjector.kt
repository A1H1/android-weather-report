package com.demo.weatherreport.di.injector

import com.demo.weatherreport.data.repository.UserRepository
import com.demo.weatherreport.data.repository.UserRepositoryImpl
import com.demo.weatherreport.data.repository.WeatherRepository
import com.demo.weatherreport.data.repository.WeatherRepositoryImpl
import org.koin.dsl.module

val repositoryInjector = module {
    single<WeatherRepository> { WeatherRepositoryImpl(get(), get()) }
    single<UserRepository> { UserRepositoryImpl(get()) }
}