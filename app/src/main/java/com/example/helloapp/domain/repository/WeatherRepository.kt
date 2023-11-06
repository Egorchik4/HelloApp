package com.example.helloapp.domain.repository

import com.example.helloapp.domain.entity.WeatherEntity

interface WeatherRepository {

	suspend fun getWeather(apiKey: String): WeatherEntity
}