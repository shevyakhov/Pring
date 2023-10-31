package com.tsu.pring.libraries.modules

import com.tsu.pring.application.activity.presentation.navigation.MainActivityRouter
import com.tsu.pring.application.activity.presentation.navigation.MainActivityRouterImpl
import com.tsu.pring.application.mainFragment.presentation.navigation.MainFragmentRouter
import com.tsu.pring.application.mainFragment.presentation.navigation.MainFragmentRouterImpl
import com.tsu.pring.features.home.presentation.navigation.HomeRouter
import com.tsu.pring.features.home.presentation.navigation.HomeRouterImpl
import com.tsu.pring.features.prediction.presentation.navigation.PredictionRouter
import com.tsu.pring.features.prediction.presentation.navigation.PredictionRouterImpl
import com.tsu.pring.features.splash.presentation.navigation.SplashFragmentRouter
import com.tsu.pring.features.splash.presentation.navigation.SplashFragmentRouterImpl
import com.tsu.pring.features.stats.presentation.navigation.StatsRouter
import com.tsu.pring.features.stats.presentation.navigation.StatsRouterImpl
import org.koin.dsl.module

val RouterModule = module {
	factory<MainActivityRouter> { MainActivityRouterImpl(get()) }
	factory<MainFragmentRouter> { MainFragmentRouterImpl(get()) }

	factory<SplashFragmentRouter> { SplashFragmentRouterImpl(get()) }
	factory<HomeRouter> { HomeRouterImpl(get()) }
	factory<StatsRouter> { StatsRouterImpl(get()) }
	factory<PredictionRouter> { PredictionRouterImpl(get()) }
}