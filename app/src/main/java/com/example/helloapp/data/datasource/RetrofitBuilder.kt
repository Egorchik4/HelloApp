package com.example.helloapp.data.datasource

import com.example.helloapp.data.api.WeatherApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

class RetrofitBuilder @Inject constructor() {

	private val url: String = "https://api.weather.yandex.ru/v2/"
	private val retrofit: Retrofit = Retrofit.Builder()
		.addConverterFactory(GsonConverterFactory.create())
		.baseUrl(url)
		.build()

	val weatherApi: WeatherApi = retrofit.create(WeatherApi::class.java)
}