package com.tsu.pring.features.stats.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tsu.pring.features.stats.presentation.navigation.StatsRouter
import com.tsu.pring.libraries.data.remote.dto.coins.CoinItem
import com.tsu.pring.libraries.domain.model.CoinChartTimeSpan
import com.tsu.pring.libraries.domain.repository.CoinRepository
import com.tsu.pring.libraries.util.Resource
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class StatsViewModel(
	private val router: StatsRouter,
	private val repository: CoinRepository,
) : ViewModel() {

	private val _state = MutableStateFlow(DetailState())
	val state: StateFlow<DetailState>
		get() = _state


	val currentCoin = MutableStateFlow<CoinItem?>(null)
	val listFlow = MutableStateFlow<List<CoinItem>>(emptyList())
	val currentList = MutableStateFlow<List<CoinItem>>(emptyList())

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

	private var getMarketChartJob: Job? = null

	fun getAllData(id: String) {
		getMarketChartJob?.cancel()
		getMarketChartJob =
			repository.getMarketChart(id, state.value.timeRange.value).onEach { result ->
				when (result) {
					is Resource.Success -> {
						_state.update { it.copy(priceList = result.data?.prices ?: emptyList()) }
					}

					is Resource.Loading -> {}

					is Resource.Error   -> {}
				}
			}.launchIn(viewModelScope)
	}

	fun getCoinDetail(id: String) {
		repository.getCoinDetail(id).onEach { result ->
			when (result) {
				is Resource.Success -> {
					_state.update {
						it.copy(coinDetail = result.data)
					}
				}

				is Resource.Loading -> {}

				is Resource.Error   -> {}
			}
		}.launchIn(viewModelScope)
	}

	fun setCoinChartTimeSpan(time: Int, id: String) {
		when (time) {
			1  -> _state.update { it.copy(timeRange = CoinChartTimeSpan.TIMESPAN_1DAYS) }
			7  -> _state.update { it.copy(timeRange = CoinChartTimeSpan.TIMESPAN_7DAYS) }
			14 -> _state.update { it.copy(timeRange = CoinChartTimeSpan.TIMESPAN_14DAYS) }
			30 -> _state.update { it.copy(timeRange = CoinChartTimeSpan.TIMESPAN_30DAYS) }
			60 -> _state.update { it.copy(timeRange = CoinChartTimeSpan.TIMESPAN_60DAYS) }
		}
		getAllData(id)
	}
}