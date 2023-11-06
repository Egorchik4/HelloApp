package com.example.helloapp.data.api

import com.example.helloapp.data.models.WeatherModel
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers

interface WeatherApi {

	@GET("forecast?")
	@Headers(
		"lat: 56.484645",
		"lon: 84.947649",
		"lang: ru_RU",
		"limit: 1",
		"hours: false",
		"extra: false"

	)
	suspend fun getWeather(@Header("X-Yandex-API-Key") apiKey: String): WeatherModel
}