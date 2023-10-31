package com.tsu.pring.features.splash

import androidx.fragment.app.Fragment
import com.tsu.pring.features.splash.ui.SplashFragment
import com.tsu.pring.libraries.navigators.FragmentDestination

class SplashDestination : FragmentDestination {

	override fun createInstance(): Fragment = SplashFragment()
}