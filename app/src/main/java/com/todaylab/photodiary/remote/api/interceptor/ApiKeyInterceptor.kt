package com.todaylab.photodiary.remote.api.interceptor

import okhttp3.Interceptor
import okhttp3.Response

/**
 * 쿼리에 app key 추가하는 인터셉터
 */
class ApiKeyInterceptor(
    private val apiKeyName: String,
    private val apiKey: String
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val originalUrl = originalRequest.url

        val newUrl = originalUrl.newBuilder()
            .addQueryParameter(apiKeyName, apiKey)
            .build()

        val newRequest = originalRequest.newBuilder()
            .url(newUrl)
            .build()

        return chain.proceed(newRequest)
    }
}