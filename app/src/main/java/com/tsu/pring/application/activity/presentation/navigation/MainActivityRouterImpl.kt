package com.tsu.pring.application.activity.presentation.navigation

import com.tsu.pring.features.splash.SplashDestination
import com.tsu.pring.libraries.navigators.GlobalRouter

class MainActivityRouterImpl(
	private val navigator: GlobalRouter,
) : MainActivityRouter {

	override fun newRootScreen() {
		navigator.newRoot(SplashDestination())
	}

}