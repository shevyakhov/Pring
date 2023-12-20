package com.tsu.pring.features.stats.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tsu.pring.features.stats.presentation.navigation.StatsRouter
import com.tsu.pring.libraries.data.local.CoinItemRepository
import com.tsu.pring.libraries.data.remote.dto.coins.CoinItem
import com.tsu.pring.libraries.domain.model.CoinChartTimeSpan
import com.tsu.pring.libraries.domain.repository.CoinRepository
import com.tsu.pring.libraries.util.Resource
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class StatsViewModel(
	private val router: StatsRouter,
	private val repository: CoinRepository,
	private val localRepository: CoinItemRepository,
) : ViewModel() {

	private val _state = MutableStateFlow(DetailState())
	val state: StateFlow<DetailState>
		get() = _state

	val eventFlow = MutableSharedFlow<Unit>()
	val currentCoin = MutableStateFlow<CoinItem?>(null)
	val listFlow = MutableStateFlow<List<CoinItem>>(emptyList())
	val currentList = MutableStateFlow<List<CoinItem>>(emptyList())

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

					is Resource.Error   -> {
						viewModelScope.launch {
							eventFlow.emit(Unit)
						}
					}
				}
			}.launchIn(viewModelScope)
	}

	fun setCoinChartTimeSpan(time: Int, id: String) {
		when (time) {
			1 -> _state.update { it.copy(timeRange = CoinChartTimeSpan.TIMESPAN_1DAYS) }
			7 -> _state.update { it.copy(timeRange = CoinChartTimeSpan.TIMESPAN_7DAYS) }
		}
		getAllData(id)
	}
}