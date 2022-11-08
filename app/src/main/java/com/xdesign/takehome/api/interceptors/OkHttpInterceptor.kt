package com.xdesign.takehome.api.interceptors

import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Singleton

@Singleton
class OkHttpInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder = chain.request().newBuilder()
        builder.header("Device-Type", "Android")
        builder.header("Accept", "application/json")
        builder.header("Authorization", "Bearer 754t!si@glcE2qmOFEcN")

        return chain.proceed(builder.build())
    }
}