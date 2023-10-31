package com.tsu.pring.features.home

import androidx.fragment.app.Fragment
import com.tsu.pring.features.home.ui.HomeFragment
import com.tsu.pring.libraries.navigators.FragmentDestination

object HomeDestination : FragmentDestination {

	override fun createInstance(): Fragment = HomeFragment.newInstance()
}