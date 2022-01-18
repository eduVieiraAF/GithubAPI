package com.example.githubapi.di

import com.example.githubapi.RecyclerViewAdapter
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
class RetroModule {

    private val baseURL = "https://api.github.com/search/"

    @Singleton
    @Provides
    fun getRetroService(retrofit: Retrofit): RetroService{

        return retrofit.create(RetroService::class.java)

    }

    @Singleton
    @Provides
    fun getRetrofitInstance(): Retrofit {

        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BASIC

        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()

        return Retrofit.Builder().baseUrl(baseURL).addConverterFactory(GsonConverterFactory
            .create()).client(client).build()
    }
}