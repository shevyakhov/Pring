package com.tsu.pring.features.home

import androidx.fragment.app.Fragment
import com.tsu.pring.features.home.ui.CoinListFragment
import com.tsu.pring.libraries.navigators.FragmentDestination

object CoinListDestination : FragmentDestination {

	override fun createInstance(): Fragment {
		return  CoinListFragment()
	}
}