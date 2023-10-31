package com.tsu.pring.libraries.navigators

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.Screen
import com.github.terrakok.cicerone.androidx.FragmentScreen

class GlobalRouterImpl(
	private val navigator: Router,
) : GlobalRouter {

	override fun open(destination: FragmentDestination) {
		navigator.navigateTo(CustomFragmentScreen(destination))
	}

	override fun replace(destination: FragmentDestination) {
		navigator.replaceScreen(CustomFragmentScreen(destination))
	}

	override fun newRoot(destination: FragmentDestination) {
		navigator.newRootScreen(CustomFragmentScreen(destination))
	}

	override fun popToRoot() {
		navigator.backTo(null)
	}

	override fun exit() {
		navigator.exit()
	}

	override fun finish() {
		navigator.finishChain()
	}

	override fun popTo(destination: Class<FragmentDestination>) {
		navigator.backTo(ScreenDefault(destination.name))
	}
}

private class CustomFragmentScreen(private val fragmentDestination: FragmentDestination) : FragmentScreen {

	override val screenKey: String = fragmentDestination::class.java.name

	override fun createFragment(factory: FragmentFactory): Fragment =
		fragmentDestination.createInstance()
}

private class ScreenDefault(override val screenKey: String) : Screen