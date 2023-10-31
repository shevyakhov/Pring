package com.tsu.pring.libraries.navigators

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.androidx.FragmentScreen
import com.tsu.pring.features.home.HomeDestination
import kotlinx.coroutines.flow.MutableStateFlow

class MainRouterImpl(private val navigator: Router) : MainRouter {

	override val destinationStateFlow = MutableStateFlow<FragmentDestination>(HomeDestination)

	private val backStack = mutableListOf<FragmentDestination>()

	override fun open(destination: FragmentDestination) {
		backStack.remove(destination)
		backStack.add(destination)
		destinationStateFlow.value = destination
		navigator.navigateTo(FoodFragmentScreen(destination))
	}

	override fun exit() {
		backStack.removeLast()
		if (backStack.isNotEmpty()) {
			open(backStack.last())
		} else {
			navigator.finishChain()
		}
	}
}

private class FoodFragmentScreen(private val destination: FragmentDestination) : FragmentScreen {

	override fun createFragment(factory: FragmentFactory): Fragment = destination.createInstance()
}