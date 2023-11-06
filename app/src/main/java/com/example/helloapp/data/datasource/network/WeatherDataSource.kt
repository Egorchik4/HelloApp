package com.example.helloapp.data.datasource.network

import com.example.helloapp.data.api.WeatherApi
import com.example.helloapp.data.models.WeatherModel
import javax.inject.Inject

class WeatherDataSourceImpl @Inject constructor(private val api: WeatherApi) : WeatherDataSource {

	override suspend fun getWeather(apiKey: String): WeatherModel =
		api.getWeather(apiKey)
}

interface WeatherDataSource {

	suspend fun getWeather(apiKey: String): WeatherModel
}