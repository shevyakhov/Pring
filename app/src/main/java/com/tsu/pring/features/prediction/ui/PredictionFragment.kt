package com.tsu.pring.features.prediction.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.tsu.pring.databinding.FragmentHomeBinding
import com.tsu.pring.databinding.FragmentPredictionBinding
import com.tsu.pring.features.home.presentation.HomeViewModel
import com.tsu.pring.features.prediction.presentation.PredictionViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class PredictionFragment : Fragment() {

	companion object {

		fun newInstance() = PredictionFragment()
	}

	private var _binding: FragmentPredictionBinding? = null
	private val binding get() = _binding!!
	private val viewModel by viewModel<PredictionViewModel>()

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?,
	): View {
		_binding = FragmentPredictionBinding.inflate(inflater, container, false)
		return binding.root
	}

}