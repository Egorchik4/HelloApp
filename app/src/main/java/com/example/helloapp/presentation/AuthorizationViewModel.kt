package com.example.helloapp.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.helloapp.domain.usecase.GetWeatherUseCase
import com.example.helloapp.domain.usecase.ValidationUseCase
import com.example.helloapp.presentation.AuthorizationFragment.Companion.API_KEY
import com.example.helloapp.presentation.AuthorizationFragment.Companion.API_KEY_ERROR
import com.example.helloapp.presentation.AuthorizationFragment.Companion.EMPTY_FIELD
import com.example.helloapp.presentation.AuthorizationFragment.Companion.ERROR_LENGTH
import com.example.helloapp.presentation.AuthorizationFragment.Companion.ERROR_MAIL
import com.example.helloapp.presentation.AuthorizationFragment.Companion.ERROR_STRING_CONTAIN_NUMBER
import com.example.helloapp.presentation.AuthorizationFragment.Companion.ERROR_STRING_LOWER_AND_UPPERCASE
import com.example.helloapp.presentation.AuthorizationFragment.Companion.NOT_FOUND
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class AuthorizationViewModel @Inject constructor(
	private val validationUseCase: ValidationUseCase,
	private val getWeatherUseCase: GetWeatherUseCase
) : ViewModel() {

	private val _validationMut = MutableLiveData<State>(State.Initial)
	val validationLive: LiveData<State> = _validationMut

	fun validateEmail(text: String): Boolean {
		return if (text.trim().isEmpty()) {
			_validationMut.value = State.ErrorValid(detailError = DetailError.ErrorMail(EMPTY_FIELD))
			false
		} else if (!validationUseCase.isValidEmail(text)) {
			_validationMut.value = State.ErrorValid(detailError = DetailError.ErrorMail(ERROR_MAIL))
			false
		} else {
			_validationMut.value = State.CorrectValid(DetailCorrect.CorrectMail)
			true
		}
	}

	fun validatePassword(text: String): Boolean {
		return if (text.trim().isEmpty()) {
			_validationMut.value = State.ErrorValid(detailError = DetailError.ErrorPassword(EMPTY_FIELD))
			false
		} else if (text.length < 6) {
			_validationMut.value = State.ErrorValid(detailError = DetailError.ErrorPassword(ERROR_LENGTH))
			false
		} else if (!validationUseCase.isStringContainNumber(text)) {
			_validationMut.value = State.ErrorValid(detailError = DetailError.ErrorPassword(ERROR_STRING_CONTAIN_NUMBER))
			false
		} else if (!validationUseCase.isStringLowerAndUpperCase(text)) {
			_validationMut.value = State.ErrorValid(detailError = DetailError.ErrorPassword(ERROR_STRING_LOWER_AND_UPPERCASE))
			false
		} else {
			_validationMut.value = State.CorrectValid(DetailCorrect.CorrectPassword)
			true
		}
	}

	fun signIn(mailText: String, passwordText: String) {
		if (validateEmail(mailText) && validatePassword(passwordText)) {
			_validationMut.value = State.Loading
			viewModelScope.launch {
				try {
					val weather = getWeatherUseCase(API_KEY)
					_validationMut.value = State.WeatherContent(weatherEntity = weather)
				} catch (e: HttpException) {
					if (e.code() == 403) {
						_validationMut.value = State.WeatherError(message = API_KEY_ERROR)
					} else if (e.code() == 404) {
						_validationMut.value = State.WeatherError(message = NOT_FOUND)
					} else {
						_validationMut.value = State.WeatherError(message = e.code().toString())
					}
				} catch (e: IOException) {
					_validationMut.value = State.WeatherError(message = e.message.toString())
				}
			}
		}
	}
}