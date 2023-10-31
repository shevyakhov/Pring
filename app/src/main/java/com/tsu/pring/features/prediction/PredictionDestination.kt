package com.tsu.pring.features.prediction

import androidx.fragment.app.Fragment
import com.tsu.pring.features.home.ui.HomeFragment
import com.tsu.pring.features.prediction.ui.PredictionFragment
import com.tsu.pring.libraries.navigators.FragmentDestination

object PredictionDestination : FragmentDestination {

	override fun createInstance(): Fragment = PredictionFragment.newInstance()
}