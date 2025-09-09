package com.pdxx.kotlinlearn.koindemo.di

import com.pdxx.kotlinlearn.koindemo.api.ApiService
import com.pdxx.kotlinlearn.koindemo.repository.VideoRepository
import com.pdxx.kotlinlearn.koindemo.ui.VideoViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {
    single {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    }

    single<ApiService> {
        Retrofit.Builder()
            .baseUrl("https://api.apiopen.top/")
            .client(get())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

    single { VideoRepository(get()) }

    viewModel { VideoViewModel(get()) }
}
