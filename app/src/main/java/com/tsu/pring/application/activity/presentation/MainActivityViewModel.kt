package com.tsu.pring.application.activity.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tsu.pring.application.activity.presentation.navigation.MainActivityRouter
import com.tsu.pring.libraries.data.local.CoinItemRepository
import com.tsu.pring.libraries.data.remote.dto.coins.CoinItem
import com.tsu.pring.libraries.domain.repository.CoinRepository
import com.tsu.pring.libraries.util.Resource
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class MainActivityViewModel(
	private val navigator: MainActivityRouter,
	private val repository: CoinRepository,
	private val localRepository: CoinItemRepository,
) : ViewModel() {

	fun openMainRoot() {
		navigator.newRootScreen()
	}

	private val eventFlow = MutableSharedFlow<Unit>()
	private val listFlow = MutableStateFlow<List<CoinItem>>(emptyList())
	private val currentList = MutableStateFlow<List<CoinItem>>(emptyList())

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
}
