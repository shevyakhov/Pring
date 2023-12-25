package com.tsu.pring.features.home.ui

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.ValueFormatter
import com.tsu.pring.databinding.FragmentHomeBinding
import com.tsu.pring.features.home.presentation.HomeViewModel
import com.tsu.pring.features.home.presentation.adapter.TrendingListAdapter
import com.tsu.pring.libraries.data.remote.dto.coins.TrendingCoinItem
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
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

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		val scope = viewLifecycleOwner.lifecycleScope

		val adapter = TrendingListAdapter {}
		binding.homeRv.adapter = adapter
		binding.homeRv.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

		viewModel.eventFlow.onEach {
			Toast.makeText(requireContext(), "Ошибка сервера", Toast.LENGTH_SHORT).show()
		}.launchIn(scope)

		binding.homeButtonList.setOnClickListener {
			viewModel.coinList()
		}
		viewModel.getCoinList()
		viewModel.currentList.onEach {
			adapter.addAll(it)
			adapter.notifyDataSetChanged()
		}.launchIn(scope)

		viewModel.currentList.onEach {
			chart(it)
		}.launchIn(scope)
	}

	private fun chart(list: List<TrendingCoinItem>) {
		val entries = ArrayList<BarEntry>()
		val labels = ArrayList<String>()
		list.forEachIndexed { index, coinItem ->
			val price = coinItem.priceBtc.toFloat()
			entries.add(BarEntry(index.toFloat(), price))
			labels.add(coinItem.name)
		}
		val barDataSet = BarDataSet(entries, "Coin Prices")
		barDataSet.color = Color.BLUE

		val barData = BarData(barDataSet)
		binding.homeBarChart.data = barData

		binding.homeBarChart.description.isEnabled = false
		binding.homeBarChart.setFitBars(true)

		val xAxis: XAxis = binding.homeBarChart.xAxis
		xAxis.position = XAxis.XAxisPosition.BOTTOM
		xAxis.setDrawGridLines(false)
		xAxis.setDrawAxisLine(true)
		xAxis.granularity = 1f
		xAxis.valueFormatter = object : ValueFormatter() {
			override fun getAxisLabel(value: Float, axis: AxisBase?): String {
				return if (value >= 0 && value < labels.size) {
					labels[value.toInt()]
				} else {
					""
				}
			}
		}
		binding.homeBarChart.description.isEnabled = false
		binding.homeBarChart.setFitBars(true)
		binding.homeBarChart.invalidate()
	}

}