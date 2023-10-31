package com.tsu.pring.features.stats

import androidx.fragment.app.Fragment
import com.tsu.pring.features.prediction.ui.PredictionFragment
import com.tsu.pring.features.stats.ui.StatsFragment
import com.tsu.pring.libraries.navigators.FragmentDestination

object StatsDestination : FragmentDestination {

	override fun createInstance(): Fragment = StatsFragment.newInstance()
}