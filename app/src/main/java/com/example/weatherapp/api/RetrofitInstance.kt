package com.example.weatherapp.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    private const val apiBaseUrl = "https://api.weatherapi.com"

    private fun getInstance(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(apiBaseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val weatherApi: WeatherApi = getInstance().create(WeatherApi::class.java)
}