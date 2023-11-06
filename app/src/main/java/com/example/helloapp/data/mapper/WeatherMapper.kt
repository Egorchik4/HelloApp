package com.example.helloapp.data.mapper

import com.example.helloapp.data.models.WeatherModel
import com.example.helloapp.domain.entity.WeatherEntity

fun WeatherModel.toEntity() =
	WeatherEntity(temp = fact.temp)