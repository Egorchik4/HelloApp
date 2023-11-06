package com.example.helloapp.data.models

import com.google.gson.annotations.SerializedName

data class WeatherModel(
	@SerializedName("fact")
	var fact: Fact
)

data class Fact(
	@SerializedName("obs_time")
	var obsTime: Int?,
	@SerializedName("uptime")
	var uptime: Int?,
	@SerializedName("temp")
	var temp: Int?,
	@SerializedName("feels_like")
	var feelsLike: Int?,
	@SerializedName("icon")
	var icon: String?,
	@SerializedName("condition")
	var condition: String?,
	@SerializedName("cloudness")
	var cloudness: Int?,
	@SerializedName("prec_type")
	var precType: Int?,
	@SerializedName("prec_prob")
	var precProb: Int?,
	@SerializedName("prec_strength")
	var precStrength: Int?,
	@SerializedName("is_thunder")
	var isThunder: Boolean?,
	@SerializedName("wind_speed")
	var windSpeed: Double?,
	@SerializedName("wind_dir")
	var windDir: String?,
	@SerializedName("pressure_mm")
	var pressureMm: Int?,
	@SerializedName("pressure_pa")
	var pressurePa: Int?,
	@SerializedName("humidity")
	var humidity: Int?,
	@SerializedName("daytime")
	var daytime: String?,
	@SerializedName("polar")
	var polar: Boolean?,
	@SerializedName("season")
	var season: String?,
	@SerializedName("source")
	var source: String?,
	@SerializedName("soil_moisture")
	var soilMoisture: Double?,
	@SerializedName("soil_temp")
	var soilTemp: Int?,
	@SerializedName("uv_index")
	var uvIndex: Int?,
	@SerializedName("wind_gust")
	var windGust: Double?
)
