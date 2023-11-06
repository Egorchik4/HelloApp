package com.example.helloapp.domain.usecase

import com.example.helloapp.domain.entity.WeatherEntity
import com.example.helloapp.domain.repository.WeatherRepository
import javax.inject.Inject

class GetWeatherUseCase @Inject constructor(private val repository: WeatherRepository) {

	suspend operator fun invoke(apiKey: String): WeatherEntity =
		repository.getWeather(apiKey)
}