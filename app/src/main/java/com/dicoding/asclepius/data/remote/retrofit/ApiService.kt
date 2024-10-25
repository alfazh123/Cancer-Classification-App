package com.dicoding.asclepius.data.remote.retrofit

import com.dicoding.asclepius.data.remote.response.NewsResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("top-headlines?q=cancer&category=health&language=en")
    fun getNews(@Query("apiKey") apiKey: String): Call<NewsResponse>
}