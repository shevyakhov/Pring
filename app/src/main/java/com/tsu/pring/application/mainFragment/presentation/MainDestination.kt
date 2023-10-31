package com.tsu.pring.application.mainFragment.presentation

import androidx.fragment.app.Fragment
import com.tsu.pring.application.mainFragment.ui.MainFragment
import com.tsu.pring.libraries.navigators.FragmentDestination

object MainDestination : FragmentDestination {

	override fun createInstance(): Fragment = MainFragment()
}