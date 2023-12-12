package com.tsu.pring.features.prediction.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.tsu.pring.databinding.FragmentPredictionBinding
import com.tsu.pring.features.prediction.presentation.PredictionViewModel
import com.tsu.pring.features.prediction.ui.recycler.CoinListAdapter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
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

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		val scope = viewLifecycleOwner.lifecycleScope

		viewModel.eventFlow.onEach {
			Toast.makeText(requireContext(), "Ошибка сервера", Toast.LENGTH_SHORT).show()
		}.launchIn(scope)

		val adapter = CoinListAdapter {
			binding.recyclerViewCoin.visibility = View.GONE
			binding.currentCoinLayout.visibility = View.VISIBLE
			viewModel.searchCoin(it)
		}
		viewModel.currentCoin.onEach {
			Glide
				.with(requireContext())
				.load(it?.image)
				.centerCrop()
				.into(binding.cryptoIcon1)
			binding.cryptoFullName1.text = it?.name
			binding.cryptoMoney.text = it?.currentPrice?.toString() + "$"
			binding.cryptoIncrease.text = buildString {
				append("high end ")
				append(it?.high24h?.toString())
				append("$")
				append("\n")
				append("low end ")
				append(it?.priceChangePercentage24h?.toString())
				append("$\n")
				append(it?.marketCapRank)
				append("%")
			}
		}.launchIn(scope)

		binding.recyclerViewCoin.layoutManager = LinearLayoutManager(requireContext())
		binding.recyclerViewCoin.adapter = adapter

		binding.buttonPredict.setOnClickListener {
			binding.prediction.text = viewModel.predict()
		}
		viewModel.currentList.onEach {
			adapter.addAll(it)
		}.launchIn(scope)

		viewModel.getCoinList()

		binding.searchTextField.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
			override fun onQueryTextSubmit(query: String?): Boolean {
				viewModel.search(query)
				binding.recyclerViewCoin.visibility = View.VISIBLE
				binding.currentCoinLayout.visibility = View.GONE
				return true
			}

			override fun onQueryTextChange(p0: String?): Boolean {
				viewModel.search(p0)
				binding.recyclerViewCoin.visibility = View.VISIBLE
				binding.currentCoinLayout.visibility = View.GONE
				return true
			}
		})
	}

}