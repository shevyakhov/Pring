package com.tsu.pring.features.home.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tsu.pring.features.prediction.presentation.navigation.PredictionRouter
import com.tsu.pring.libraries.data.local.CoinItemRepository
import com.tsu.pring.libraries.data.remote.dto.coins.CoinItem
import com.tsu.pring.libraries.data.remote.dto.search.CoinSearchResponse
import com.tsu.pring.libraries.domain.repository.CoinRepository
import com.tsu.pring.libraries.util.Resource
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class ListViewModel(
	private val router: PredictionRouter,
	private val repository: CoinRepository,
	private val localRepository: CoinItemRepository,
) : ViewModel() {

	val eventFlow = MutableSharedFlow<Unit>()
	val listFlow = MutableStateFlow<List<CoinItem>>(emptyList())
	val currentList = MutableStateFlow<List<CoinItem>>(emptyList())

	fun getCoinList() = viewModelScope.launch {
		getLocalData().await()
		if (listFlow.value.isEmpty()) {

		}
	}

	private fun getLocalData() = viewModelScope.async {
		listFlow.value = localRepository.getAllCoinItems()
		currentList.value = listFlow.value
	}

	private suspend fun saveRemoteData(result: Resource<List<CoinItem>>) = viewModelScope.launch {
		listFlow.value = result.data ?: emptyList()
		currentList.value = listFlow.value
		localRepository.insertCoinItems(listFlow.value)
	}

	fun search(text: String?) {
		val newList = if (text.isNullOrEmpty()) {
			listFlow.value
		} else
			listFlow.value.filter {
				text.lowercase() in (it.name?.lowercase() ?: "") || text.lowercase() in (it.symbol?.lowercase() ?: "")
			}
		if (newList.isEmpty()) {
			repository.search(text.toString()).onEach { result ->
				when (result) {
					is Resource.Success -> {
						currentList.value = result.data?.coins?.map { it.toCoinItem() } ?: emptyList()
					}

					is Resource.Loading -> {}

					is Resource.Error   -> {
						viewModelScope.launch {
							eventFlow.emit(Unit)
						}
					}
				}
			}.launchIn(viewModelScope)
		} else
			currentList.value = newList
	}

	suspend fun getPrice(it: CoinItem): Double {
		val res = viewModelScope.async {
			repository.getMarketChartResult(it.id.toString(), 1).prices.last().last()
		}.await()
		return res
	}

	private fun CoinSearchResponse.toCoinItem(): CoinItem =
		CoinItem(
			id,
			symbol,
			name,
			imageUrl,
			0.0,
			0.0,
			marketCapRank.toLong(),
			null,
			0.0,
			0.0,
			0.0,
			0.0,
			0.0,
			0.0,
			0.0,
			0.0,
			null,
			null,
			0.0,
			0.0,
			null,
			0.0,
			0.0,
			null,
			null,
			null,
			0.0
		)
}