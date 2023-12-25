package com.tsu.pring.features.home.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tsu.pring.features.home.presentation.navigation.HomeRouter
import com.tsu.pring.features.stats.presentation.DetailState
import com.tsu.pring.libraries.data.local.CoinItemRepository
import com.tsu.pring.libraries.data.remote.dto.coins.CoinMarketChart
import com.tsu.pring.libraries.data.remote.dto.coins.TrendingCoinItem
import com.tsu.pring.libraries.domain.repository.CoinRepository
import com.tsu.pring.libraries.util.Resource
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class HomeViewModel(
	private val router: HomeRouter,
	private val repository: CoinRepository,
	private val localRepository: CoinItemRepository,
) : ViewModel() {

	private val _state = MutableStateFlow(DetailState())
	val state: StateFlow<DetailState>
		get() = _state

	val eventFlow = MutableSharedFlow<Unit>()
	val listFlow = MutableStateFlow<List<TrendingCoinItem>>(emptyList())
	val currentList = MutableStateFlow<List<TrendingCoinItem>>(emptyList())

	fun getCoinList() = viewModelScope.launch {
		if (listFlow.value.isEmpty()) {
			repository.getTrendingCoins().onEach { result ->
				when (result) {
					is Resource.Success -> {
						saveRemoteData(result.data?.map { it.item } ?: emptyList())
					}

					is Resource.Loading -> {
					}

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
		listFlow.value = localRepository.getAllTrendingCoinItems()
		currentList.value = listFlow.value.take(5)
	}

	private suspend fun saveRemoteData(result: List<TrendingCoinItem>) = viewModelScope.launch {
		listFlow.value = result
		currentList.value = listFlow.value.take(5)
		localRepository.insertTrendingCoinItems(listFlow.value)
	}


	fun coinList() {
		router.coinList()
	}

}