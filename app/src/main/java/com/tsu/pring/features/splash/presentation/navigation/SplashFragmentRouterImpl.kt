package com.tsu.pring.features.splash.presentation.navigation

import com.tsu.pring.application.mainFragment.presentation.MainDestination
import com.tsu.pring.libraries.navigators.GlobalRouter

class SplashFragmentRouterImpl(
	private val router: GlobalRouter,
) : SplashFragmentRouter {

	override fun goToMain() {
		router.replace(MainDestination)
	}
}