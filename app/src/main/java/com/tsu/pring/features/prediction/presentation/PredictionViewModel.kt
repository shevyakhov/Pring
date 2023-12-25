package com.tsu.pring.features.prediction.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tsu.pring.features.prediction.presentation.navigation.PredictionRouter
import com.tsu.pring.libraries.data.local.CoinItemRepository
import com.tsu.pring.libraries.data.remote.dto.coins.CoinItem
import com.tsu.pring.libraries.domain.model.CoinChartTimeSpan
import com.tsu.pring.libraries.domain.repository.CoinRepository
import com.tsu.pring.libraries.util.Resource
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class PredictionViewModel(
	private val router: PredictionRouter,
	private val repository: CoinRepository,
	private val localRepository: CoinItemRepository,
) : ViewModel() {

	val eventFlow = MutableSharedFlow<Unit>()
	val currentCoin = MutableStateFlow<CoinItem?>(null)
	val currentCoinPrices = MutableStateFlow<List<List<Double>>>(emptyList())
	val listFlow = MutableStateFlow<List<CoinItem>>(emptyList())
	val currentList = MutableStateFlow<List<CoinItem>>(emptyList())
	private var getMarketChartJob: Job? = null

	fun getCoinList() = viewModelScope.launch {
		getLocalData().await()
		if (listFlow.value.isEmpty()) {
			repository.getCoins().onEach { result ->
				when (result) {
					is Resource.Success -> {
						saveRemoteData(result)
					}

					is Resource.Loading -> {}

					is Resource.Error   -> {
						viewModelScope.launch {
							eventFlow.emit(Unit)
						}
					}
				}
			}.launchIn(viewModelScope)
		}
	}

	private fun getLocalData() = viewModelScope.async {
		listFlow.value = localRepository.getAllCoinItems()
		currentList.value = listFlow.value.take(300)
	}

	private suspend fun saveRemoteData(result: Resource<List<CoinItem>>) = viewModelScope.launch {
		listFlow.value = result.data ?: emptyList()
		currentList.value = listFlow.value.take(300)
		localRepository.insertCoinItems(listFlow.value)
	}

	fun search(text: String?) {
		if (text.isNullOrEmpty()) {
			currentList.value = listFlow.value.take(300)
		} else
			currentList.value = listFlow.value.filter {
				text.lowercase() in (it.name?.lowercase() ?: "") || text.lowercase() in (it.symbol?.lowercase() ?: "")
			}
	}

	fun searchCoin(it: CoinItem) {
		viewModelScope.launch {
			currentCoin.value = it
			getAllData(it.id ?: return@launch)
			currentList.value = listFlow.value.take(300)

		}
	}

	fun getAllData(id: String) {
		getMarketChartJob?.cancel()
		getMarketChartJob = repository.getMarketChart(id, CoinChartTimeSpan.TIMESPAN_7DAYS.value).onEach { result ->
			when (result) {
				is Resource.Success -> {
					currentCoinPrices.value = result.data?.prices ?: emptyList()
				}

				is Resource.Loading -> {}

				is Resource.Error   -> {
					viewModelScope.launch {
						eventFlow.emit(Unit)
					}
				}
			}
		}.launchIn(viewModelScope)
	}

	fun predict(): String {
		return try {
			val size = currentCoinPrices.value.size
			val isIncrement = currentCoinPrices.value[size - 1].component2() > currentCoinPrices.value[size - 2].component2()

			if (isIncrement) "Возрастет" else "Упадет"
		} catch (_: Exception) {
			"Упадет"
		}
	}
}