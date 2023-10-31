package com.tsu.pring.features.home.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.tsu.pring.databinding.FragmentHomeBinding
import com.tsu.pring.features.home.presentation.HomeViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {

	companion object {

		fun newInstance() = HomeFragment()
	}

	private var _binding: FragmentHomeBinding? = null
	private val binding get() = _binding!!
	private val viewModel by viewModel<HomeViewModel>()

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?,
	): View {
		_binding = FragmentHomeBinding.inflate(inflater, container, false)
		return binding.root
	}

}