package com.todaylab.cleanarchtemplate.remote.di

import javax.inject.Qualifier

/**
 * remote layer
 * qualifier annotations for di
 */

/**
 * qualify annotations for weather api retrofit
 * used to distinguish between google and open weather api
 */
@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class GoogleWeatherRetrofit

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class OpenWeatherRetrofit