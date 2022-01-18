package com.example.githubapi.di

import com.example.githubapi.model.RecyclerList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RetroService {

    @GET("repositories")
    fun getAPIData(@Query("q") query: String, @Query("page") page: Int,
        @Query("per_page") pageSize: Int = 20): Call<RecyclerList>?
}