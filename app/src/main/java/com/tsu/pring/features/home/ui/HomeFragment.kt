package com.tsu.pring.features.home.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.tsu.pring.databinding.FragmentHomeBinding
import com.tsu.pring.features.home.presentation.HomeViewModel
import com.tsu.pring.features.home.recycler.TrendingListAdapter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
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
		_binding?.cryptoListBrn?.setOnClickListener {
//			viewModel.getCoinList()
		}
		return binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		val scope = viewLifecycleOwner.lifecycleScope

		viewModel.eventFlow.onEach {
			Toast.makeText(requireContext(), "Ошибка сервера", Toast.LENGTH_SHORT).show()
		}.launchIn(scope)


		val adapter = TrendingListAdapter {
			binding.trendingRecyclerView.visibility = View.VISIBLE
			binding.searchRecyclerView.visibility = View.GONE
		}

		binding.trendingRecyclerView.layoutManager = LinearLayoutManager(requireContext())
		binding.trendingRecyclerView.adapter = adapter
//		binding.searchRecyclerView.layoutManager = LinearLayoutManager(requireContext())
//		binding.searchRecyclerView.adapter = adapter


	}

}