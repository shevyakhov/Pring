package com.tsu.pring.application.mainFragment.presentation.navigation

import com.tsu.pring.application.mainFragment.MainScreenValue
import com.tsu.pring.features.home.HomeDestination
import com.tsu.pring.features.prediction.PredictionDestination
import com.tsu.pring.features.stats.StatsDestination
import com.tsu.pring.libraries.navigators.MainRouter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MainFragmentRouterImpl(private val navigator: MainRouter) : MainFragmentRouter {

	override val screenFlow: Flow<MainScreenValue> = navigator.destinationStateFlow.map {
		when (it) {
			HomeDestination  -> MainScreenValue.Home
			StatsDestination   -> MainScreenValue.Stats
			PredictionDestination -> MainScreenValue.Prediction
			else           -> throw Error("cannot handle ${it::class.simpleName}")
		}
	}

	override fun navigateToHome() {
		navigator.open(HomeDestination)
	}

	override fun navigateToStats() {
		navigator.open(StatsDestination)
	}

	override fun navigateToPrediction() {
		navigator.open(PredictionDestination)
	}

	override fun goBack() {
		navigator.exit()
	}
}