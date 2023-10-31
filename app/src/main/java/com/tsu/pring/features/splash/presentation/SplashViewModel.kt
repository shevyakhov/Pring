package com.tsu.pring.features.splash.presentation

import androidx.lifecycle.ViewModel
import com.tsu.pring.features.splash.presentation.navigation.SplashFragmentRouter

class SplashViewModel(private val router: SplashFragmentRouter) : ViewModel() {

	fun init() {
		router.goToMain()
	}

}