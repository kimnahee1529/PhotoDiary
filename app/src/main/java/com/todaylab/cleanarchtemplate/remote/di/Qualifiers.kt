package com.todaylab.cleanarchtemplate.remote.di

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class GoogleWeatherRetrofit

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class OpenWeatherRetrofit