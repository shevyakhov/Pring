package com.tsu.pring.features.stats.ui

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import com.tsu.pring.R
import com.tsu.pring.databinding.FragmentStatsBinding
import com.tsu.pring.features.prediction.ui.recycler.CoinListAdapter
import com.tsu.pring.features.stats.presentation.StatsViewModel
import com.tsu.pring.libraries.data.remote.dto.coins.CoinItem
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

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

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		val scope = viewLifecycleOwner.lifecycleScope
		observeData()

		val adapter = CoinListAdapter {
			binding.rv.visibility = View.GONE
			viewModel.currentCoin.value = it
			selectTimeSpan(it)
		}
		viewModel.currentCoin.onEach {
			Glide
				.with(requireContext())
				.load(it?.image)
				.centerCrop()
				.into(binding.coinIcon)
			binding.tvCoinName.text = it?.name
			binding.tvCurrentPrice.text = it?.currentPrice?.toString() + "$"
			binding.tvLow24h.text = buildString {
				append(it?.low24h)
				append("$")
			}
			binding.tvHigh24h.text = buildString {
				append(it?.high24h)
				append("$")
			}
			binding.tvCoinSymbol.text = it?.symbol

			it?.id?.let { id ->
				when (binding.radioGroup.checkedRadioButtonId) {
					binding.radioButton1.id -> viewModel.setCoinChartTimeSpan(1, id)
					binding.radioButton7.id -> viewModel.setCoinChartTimeSpan(7, id)
				}
			}

		}.launchIn(scope)
		binding.rv.layoutManager = LinearLayoutManager(requireContext())
		binding.rv.adapter = adapter

		viewModel.currentList.onEach {
			adapter.addAll(it)
		}.launchIn(scope)

		viewModel.getCoinList()

		binding.searchBar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
			override fun onQueryTextSubmit(query: String?): Boolean {
				viewModel.search(query)
				binding.rv.visibility = View.VISIBLE
				return true
			}

			override fun onQueryTextChange(p0: String?): Boolean {
				viewModel.search(p0)
				binding.rv.visibility = View.VISIBLE
				return true
			}
		})
	}

	private fun observeData() {
		viewModel.eventFlow.onEach {
			Toast.makeText(requireContext(), "Ошибка сервера", Toast.LENGTH_SHORT).show()
		}.launchIn(viewLifecycleOwner.lifecycleScope)

		lifecycleScope.launch {
			repeatOnLifecycle(Lifecycle.State.STARTED) {
				viewModel.state.collectLatest { state ->
					val chartData = getData(state.priceList)
					displayLineChart(chartData)
				}
			}
		}
	}

	private fun selectTimeSpan(item: CoinItem): String {
		if (!item.id.isNullOrEmpty()) {
			with(binding) {
				radioGroup.setOnCheckedChangeListener { _, checkedId ->
					when (checkedId) {
						radioButton1.id -> viewModel.setCoinChartTimeSpan(1, item.id)
						radioButton7.id -> viewModel.setCoinChartTimeSpan(7, item.id)
					}
				}
			}
		}
		return ""
	}

	private fun displayLineChart(chartData: Pair<List<String>, List<Entry>>) {
		binding.lineChart.apply {
			val lineDataSet = LineDataSet(chartData.second, "Value")
			lineDataSet.setDrawFilled(true)
			val formatter = object : ValueFormatter() {
				override fun getAxisLabel(value: Float, axis: AxisBase?): String {
					return if (value.toInt() < chartData.first.size) chartData.first[value.toInt()] else ""
				}
			}
			xAxis.position = XAxis.XAxisPosition.BOTTOM

			lineDataSet.mode = LineDataSet.Mode.HORIZONTAL_BEZIER
			xAxis.valueFormatter = formatter
			xAxis.labelRotationAngle = 90f

			lineDataSet.setDrawCircles(false)
			lineDataSet.disableDashedLine()
			lineDataSet.setDrawValues(false)
			description.text = "Usd"
			val data = LineData(lineDataSet)
			this.data = data
			lineDataSet.fillDrawable = lineFillDrawable(viewModel.currentCoin.value ?: return)
			invalidate()

			animateX(1000, Easing.EaseInExpo)
		}
	}

	private fun lineFillDrawable(item: CoinItem): Drawable? {
		return if (item.priceChangePercentage24h > 0) {
			ContextCompat.getDrawable(requireContext(), R.drawable.chart_bg_increase)
		} else {
			ContextCompat.getDrawable(requireContext(), R.drawable.chart_bg_decrease)
		}
	}

	private fun getData(list: List<List<Double>>): Pair<List<String>, List<Entry>> {
		val xAxisValues = arrayListOf<String>()
		val entries = arrayListOf<Entry>()

		val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.ROOT)

		list.forEachIndexed { index, entry ->
			val date = Date(entry[0].toLong())
			val label = simpleDateFormat.format(date)
			xAxisValues.add(label)
			entries.add(Entry(index.toFloat(), entry[1].toFloat()))
		}

		return Pair(xAxisValues, entries)
	}
}