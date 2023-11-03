package com.example.helloapp.presentation

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.helloapp.databinding.FragmentAuthorizationBinding
import com.example.helloapp.domain.usecase.ShowSnackBarUseCase
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AuthorizationFragment : Fragment() {

	private lateinit var binding: FragmentAuthorizationBinding
	private val viewModel: AuthorizationViewModel by viewModels()

	@Inject
	lateinit var snackBarUseCase: ShowSnackBarUseCase

	companion object {

		const val API_KEY = "0a0b7709-45eb-430d-96dc-50a4704f68c0"
		const val MESSAGE = "Weather in Tomsk, °C: "

		const val EMPTY_FIELD = "Required Field!"
		const val ERROR_MAIL = "Invalid Email!"
		const val ERROR_LENGTH = "Password can't be less than 6"
		const val ERROR_STRING_CONTAIN_NUMBER = "Required at least 1 digit"
		const val ERROR_STRING_LOWER_AND_UPPERCASE = "Password must contain upper and lower case letters"

		const val API_KEY_ERROR = "Maybe Api key problem"
		const val NOT_FOUND = "Not found"
	}

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
		binding = FragmentAuthorizationBinding.inflate(inflater)
		return binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		setObservers()
		setListeners()
	}

	private fun setObservers() {
		viewModel.validationLive.observe(viewLifecycleOwner) {
			when (it) {
				is State.Initial        -> renderInitial()
				is State.CorrectValid   -> renderCorrect(it)
				is State.ErrorValid     -> renderError(it)
				is State.Loading        -> renderLoading()
				is State.WeatherContent -> renderContent(it)
				is State.WeatherError   -> renderWeatherError(it)
			}
		}
	}

	private fun renderInitial() {
		with(binding) {
			progressBar.visibility = View.GONE
			buttonSignIn.visibility = View.VISIBLE
			buttonCreate.visibility = View.GONE
		}
	}

	private fun renderCorrect(state: State.CorrectValid) {
		when (state.detailCorrect) {
			is DetailCorrect.CorrectMail     -> {
				binding.editTextLayoutMail.isErrorEnabled = false
			}

			is DetailCorrect.CorrectPassword -> {
				binding.editTextLayoutPassword.isErrorEnabled = false
			}
		}
	}

	private fun renderError(state: State.ErrorValid) {
		when (state.detailError) {
			is DetailError.ErrorMail     -> {
				binding.editTextLayoutMail.error = state.detailError.message
				if (state.detailError.message == EMPTY_FIELD) {
					binding.buttonCreate.visibility = View.GONE
				} else {
					binding.buttonCreate.visibility = View.VISIBLE
				}
			}

			is DetailError.ErrorPassword -> {
				binding.editTextLayoutPassword.error = state.detailError.message
			}
		}
	}

	private fun renderLoading() {
		with(binding) {
			progressBar.visibility = View.VISIBLE
			buttonSignIn.visibility = View.GONE
		}
	}

	private fun renderContent(content: State.WeatherContent) {
		with(binding) {
			progressBar.visibility = View.GONE
			buttonSignIn.visibility = View.VISIBLE
			snackBarUseCase.showSnackBar(buttonSignIn, MESSAGE + content.weatherEntity.temp.toString())
		}

	}

	private fun renderWeatherError(text: State.WeatherError) {
		with(binding) {
			progressBar.visibility = View.GONE
			buttonSignIn.visibility = View.VISIBLE
			snackBarUseCase.showSnackBar(binding.buttonSignIn, text.message)
		}
	}

	private fun setListeners() {
		with(binding) {
			editTextMail.addTextChangedListener {
				viewModel.validateEmail(it.toString())
			}
			editTextPassword.addTextChangedListener {
				viewModel.validatePassword(it.toString())
			}
			buttonSignIn.setOnClickListener {
				hideKeyboard(it)
				viewModel.signIn(
					editTextMail.text.toString(),
					editTextPassword.text.toString()
				)
			}
		}
	}

	private fun hideKeyboard(view: View) {
		val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
		imm?.hideSoftInputFromWindow(view.windowToken, 0)
	}
}