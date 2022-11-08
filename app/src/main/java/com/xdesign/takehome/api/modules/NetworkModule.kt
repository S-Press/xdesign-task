package com.xdesign.takehome.api.modules

import android.app.Application
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.xdesign.takehome.BuildConfig
import com.xdesign.takehome.api.CharacterAPI
import com.xdesign.takehome.api.executors.NewThreadPerTaskExecutor
import com.xdesign.takehome.api.interceptors.OkHttpInterceptor
import com.xdesign.takehome.util.jsonDefaultInstance
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import okhttp3.Cache
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.Executor
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {
    companion object {
        private const val OKHTTP_CLIENT = "OkHttpClient"
        private const val CACHE_SIZE = 10L * 1024 * 1024
        private const val TIMEOUT = 30L
        private const val BASE_URL = "https://yj8ke8qonl.execute-api.eu-west-1.amazonaws.com/"
    }

    @Provides
    @Singleton
    fun provideInterceptor(): OkHttpInterceptor {
        return OkHttpInterceptor()
    }

    @Provides
    @Singleton
    @Named(OKHTTP_CLIENT)
    fun provideOkHttpClient(
        application: Application,
        interceptor: OkHttpInterceptor
    ): OkHttpClient {
        val builder = OkHttpClient.Builder()
        builder.cache(Cache(application.cacheDir, CACHE_SIZE))
            .writeTimeout(TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(TIMEOUT, TimeUnit.SECONDS).connectTimeout(TIMEOUT, TimeUnit.SECONDS)

        builder.interceptors().add(interceptor)

        if (BuildConfig.DEBUG) {
            val httpLoggingInterceptor = HttpLoggingInterceptor()
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            builder.networkInterceptors().add(httpLoggingInterceptor)
        }

        return builder.build()
    }

    @ExperimentalSerializationApi
    @Provides
    @Singleton
    fun provideApi(@Named(OKHTTP_CLIENT) okHttpClient: OkHttpClient): CharacterAPI {
        val contentType = "application/json".toMediaType()
        return Retrofit.Builder().addConverterFactory(jsonDefaultInstance.asConverterFactory(contentType)).baseUrl(
            BASE_URL).client(okHttpClient).build().create(CharacterAPI::class.java)
    }

    @Provides
    fun provideNewThreadPerTaskExecutor(): Executor {
        return NewThreadPerTaskExecutor()
    }
}