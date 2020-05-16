package com.sujewan.sph.di

import com.sujewan.sph.api.DataUsageApi
import com.sujewan.sph.api.LiveDataCallAdapterFactory
import com.sujewan.sph.utils.Constants
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val networkModule = module {
    factory { provideInterceptor() }
    factory { providesOkHttpClient(get()) }
    factory { provideApiService(get()) }
    single { provideRetrofit(get()) }
}

fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(LiveDataCallAdapterFactory())
        .build()
}

fun providesOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
    return OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor)
        .connectTimeout(10000, TimeUnit.SECONDS)
        .writeTimeout(10000, TimeUnit.SECONDS)
        .readTimeout(30000, TimeUnit.SECONDS)
        .build()
}

fun provideInterceptor(): HttpLoggingInterceptor {
    val interceptor = HttpLoggingInterceptor()
    interceptor.level = HttpLoggingInterceptor.Level.BODY
    return interceptor
}

fun provideApiService(retrofit: Retrofit): DataUsageApi {
    return retrofit.create(DataUsageApi::class.java)
}