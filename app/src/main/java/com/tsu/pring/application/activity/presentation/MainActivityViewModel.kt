package com.tsu.pring.application.activity.presentation

import androidx.lifecycle.ViewModel
import com.tsu.pring.application.activity.presentation.navigation.MainActivityRouter

class MainActivityViewModel(
	private val navigator: MainActivityRouter,
) : ViewModel() {

	fun openMainRoot() {
		navigator.newRootScreen()
	}
}
