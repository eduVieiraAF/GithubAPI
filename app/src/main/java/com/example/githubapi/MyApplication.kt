package com.example.githubapi

import android.app.Application
import com.example.githubapi.di.DaggerRetroComponent
import com.example.githubapi.di.RetroComponent
import com.example.githubapi.di.RetroModule


class MyApplication: Application() {

    private lateinit var retroComponent: RetroComponent

    override fun onCreate() {

        super.onCreate()

        retroComponent = DaggerRetroComponent.builder().retroModule(RetroModule()).build()
    }

    fun getRetroComponent(): RetroComponent{

        return retroComponent
    }
}