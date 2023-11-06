package com.example.helloapp.domain.usecase

import android.content.Context
import android.view.View
import com.example.helloapp.R
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import javax.inject.Inject

class ShowSnackBarUseCase @Inject constructor(private val context: Context) {

	fun showSnackBar(view: View, message: String) {
		Snackbar.make(view, message, Snackbar.LENGTH_LONG)
			.setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_FADE)
			.setBackgroundTint(context.getColor(R.color.main_color))
			.show()
	}
}