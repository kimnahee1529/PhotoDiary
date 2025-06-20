package com.todaylab.cleanarchtemplate.remote.api.interceptor

import okhttp3.Interceptor
import okhttp3.Response

class ApiKeyInterceptor(
    private val apiKey: String
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val originalUrl = originalRequest.url

        // 쿼리에 appid 추가
        val newUrl = originalUrl.newBuilder()
            .addQueryParameter("appid", apiKey)
            .build()

        val newRequest = originalRequest.newBuilder()
            .url(newUrl)
            .build()

        return chain.proceed(newRequest)
    }
}