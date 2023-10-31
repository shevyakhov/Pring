package com.tsu.pring.application.mainFragment.presentation.navigation

import com.tsu.pring.application.mainFragment.MainScreenValue
import kotlinx.coroutines.flow.Flow

interface MainFragmentRouter {

	val screenFlow: Flow<MainScreenValue>

	fun navigateToHome()

	fun navigateToStats()

	fun navigateToPrediction()

	fun goBack()
}