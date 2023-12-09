package com.tsu.pring.features.prediction.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tsu.pring.features.prediction.presentation.navigation.PredictionRouter
import com.tsu.pring.libraries.data.remote.dto.coins.CoinItem
import com.tsu.pring.libraries.domain.model.CoinChartTimeSpan
import com.tsu.pring.libraries.domain.repository.CoinRepository
import com.tsu.pring.libraries.util.Resource
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class PredictionViewModel(
	private val router: PredictionRouter,
	private val repository: CoinRepository,
) : ViewModel() {

	val currentCoin = MutableStateFlow<CoinItem?>(null)
	val currentCoinPrices = MutableStateFlow<List<List<Double>>>(emptyList())
	val listFlow = MutableStateFlow<List<CoinItem>>(emptyList())
	val currentList = MutableStateFlow<List<CoinItem>>(emptyList())
	private var getMarketChartJob: Job? = null

	fun getCoinList() = viewModelScope.launch {
		repository.getCoins().onEach { result ->
			when (result) {
				is Resource.Success -> {
					listFlow.value = result.data ?: emptyList()
					currentList.value = listFlow.value.take(300)
				}

				is Resource.Loading -> {}

				is Resource.Error   -> {}
			}
		}.launchIn(viewModelScope)

	}

	fun search(text: String?) {
		if (text.isNullOrEmpty()) {
			currentList.value = listFlow.value.take(300)
		} else
			currentList.value = listFlow.value.filter { text in (it.name ?: "") }
	}

	fun searchCoin(it: CoinItem) = viewModelScope.launch {
		getCoinDetail(it)
		getAllData(it.id ?: return@launch)
		currentList.value = listFlow.value.take(300)
	}

	fun getCoinDetail(id: CoinItem) {
		currentCoin.value = id
	}

	fun getAllData(id: String) {
		getMarketChartJob?.cancel()
		getMarketChartJob =
			repository.getMarketChart(id, CoinChartTimeSpan.TIMESPAN_7DAYS.value).onEach { result ->
				when (result) {
					is Resource.Success -> {
						currentCoinPrices.value = result.data?.prices ?: emptyList()
					}

					is Resource.Loading -> {}

					is Resource.Error   -> {}
				}
			}.launchIn(viewModelScope)
	}

	fun predict(): String {
		val increment = currentCoinPrices.value.mapIndexed { index, doubles ->
			if (index != 0) {
				doubles.component2() > currentCoinPrices.value[index - 1][1]
			} else
				true
		}
		val prediction = increment.count { it } / increment.size.toDouble() >= 0.5

		return if (prediction) "Возрастет" else "Упадет"
	}
}