package com.tsu.pring.features.stats.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.tsu.pring.databinding.FragmentPredictionBinding
import com.tsu.pring.databinding.FragmentStatsBinding
import com.tsu.pring.features.prediction.presentation.PredictionViewModel
import com.tsu.pring.features.stats.presentation.StatsViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class StatsFragment : Fragment() {

	companion object {

		fun newInstance() = StatsFragment()
	}

	private var _binding: FragmentStatsBinding? = null
	private val binding get() = _binding!!
	private val viewModel by viewModel<StatsViewModel>()

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?,
	): View {
		_binding = FragmentStatsBinding.inflate(inflater, container, false)
		return binding.root
	}

}