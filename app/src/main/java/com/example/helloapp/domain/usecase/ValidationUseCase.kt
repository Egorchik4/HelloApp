package com.example.helloapp.domain.usecase

import android.util.Patterns
import java.util.regex.Pattern
import javax.inject.Inject

class ValidationUseCase @Inject constructor() {

	fun isValidEmail(email: String): Boolean {
		return Patterns.EMAIL_ADDRESS.matcher(email).matches()
	}

	fun isStringContainNumber(text: String): Boolean {
		val pattern = Pattern.compile(".*\\d.*")
		val matcher = pattern.matcher(text)
		return matcher.matches()
	}

	fun isStringLowerAndUpperCase(text: String): Boolean {
		val lowerCasePattern = Pattern.compile(".*[a-z].*")
		val upperCasePattern = Pattern.compile(".*[A-Z].*")
		val lowerCasePatterMatcher = lowerCasePattern.matcher(text)
		val upperCasePatterMatcher = upperCasePattern.matcher(text)
		return if (!lowerCasePatterMatcher.matches()) {
			false
		} else upperCasePatterMatcher.matches()
	}
}