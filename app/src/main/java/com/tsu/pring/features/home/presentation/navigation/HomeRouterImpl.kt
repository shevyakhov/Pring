package com.tsu.pring.features.home.presentation.navigation

import com.tsu.pring.features.home.CoinListDestination
import com.tsu.pring.libraries.navigators.GlobalRouter

class HomeRouterImpl(
	private val router: GlobalRouter,
) : HomeRouter {

	override fun coinList() {
		router.open(CoinListDestination)
	}
}