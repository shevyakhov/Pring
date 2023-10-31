package com.tsu.pring.application.mainFragment.presentation

import androidx.lifecycle.ViewModel
import com.tsu.pring.application.mainFragment.MainScreenValue
import com.tsu.pring.application.mainFragment.presentation.navigation.MainFragmentRouter
import kotlinx.coroutines.flow.Flow

class MainFragmentViewModel(
	private val navigator: MainFragmentRouter,
) : ViewModel() {

	val mainScreenValueFlow: Flow<MainScreenValue> = navigator.screenFlow

	init {
		navigator.navigateToHome()
	}

	fun navigateBack() {
		navigator.goBack()
	}

	fun navigateToHome() {
		navigator.navigateToHome()
	}

	fun navigateToStats() {
		navigator.navigateToStats()
	}

	fun navigateToPrediction() {
		navigator.navigateToPrediction()
	}

}