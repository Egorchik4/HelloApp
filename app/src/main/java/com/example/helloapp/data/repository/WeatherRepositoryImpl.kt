package com.example.helloapp.data.repository

import com.example.helloapp.data.datasource.network.WeatherDataSource
import com.example.helloapp.data.mapper.toEntity
import com.example.helloapp.domain.entity.WeatherEntity
import com.example.helloapp.domain.repository.WeatherRepository
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(private val dataSource: WeatherDataSource) : WeatherRepository {

	override suspend fun getWeather(apiKey: String): WeatherEntity =
		dataSource.getWeather(apiKey).toEntity()
}