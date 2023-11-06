package com.example.helloapp.presentation

import com.example.helloapp.domain.entity.WeatherEntity

sealed class State {

	object Initial : State()

	data class CorrectValid(val detailCorrect: DetailCorrect) : State()

	data class ErrorValid(val detailError: DetailError) : State()

	object Loading : State()

	data class WeatherContent(val weatherEntity: WeatherEntity) : State()

	data class WeatherError(val message: String) : State()
}

sealed class DetailCorrect {
	object CorrectMail : DetailCorrect()
	object CorrectPassword : DetailCorrect()
}

sealed class DetailError {
	data class ErrorMail(val message: String) : DetailError()
	data class ErrorPassword(val message: String) : DetailError()
}