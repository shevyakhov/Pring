package com.tsu.pring.features.home.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tsu.pring.data.model.CoinGet
import com.tsu.pring.data.rest.RestWorker
import com.tsu.pring.features.common.COIN_LIST_DETERMINANT
import com.tsu.pring.features.home.presentation.navigation.HomeRouter
import com.tsu.pring.libraries.data.local.CoinItemRepository
import com.tsu.pring.libraries.data.remote.dto.coins.CoinItem
import com.tsu.pring.libraries.data.remote.dto.coins.TrendingCoinItem
import com.tsu.pring.libraries.domain.repository.CoinRepository
import com.tsu.pring.libraries.util.Resource
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    private val router: HomeRouter,
    private val repository: CoinRepository,
    private val localRepository: CoinItemRepository
//	private val workManager: WorkManager,
) : ViewModel() {

    val coinListState = MutableStateFlow<List<CoinItem>>(emptyList())
    val trendingCoinListState = MutableStateFlow<List<CoinItem>>(emptyList())
    val filterList = MutableStateFlow<List<CoinItem>>(emptyList())
    val eventFlow = MutableSharedFlow<Unit>()

    init {
        viewModelScope.launch {
            coinListState.update {
                localRepository.getAllCoinItems()
            }
            repository.getTrendingCoins().take(3).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        result.data?.onEach { coinItem ->
                            val coin = coinListState.value.find { list ->
                                list.name == coinItem.item.name
                            }
                            coin?.let {
                                trendingCoinListState.update { prevState ->
                                    prevState + it
                                }
                            }
                        }
                    }

                    is Resource.Loading -> {}

                    is Resource.Error -> {
                        viewModelScope.launch {
                            eventFlow.emit(Unit)
                        }
                    }
                }
            }

            Log.i(TAG, "trendingCoinListState: ${trendingCoinListState.value}")
            Log.i(TAG, "coinListState: ${coinListState.value}")
        }
    }


    fun search(text: String?) {
        if (!text.isNullOrEmpty()) {
            filterList.update {
                coinListState.value.filter {
                    text.lowercase() in (it.name?.lowercase() ?: "") || text.lowercase() in (it.symbol?.lowercase() ?: "")
                }
            }
        }
    }

    companion object {
        val TAG = HomeViewModel::class.java.simpleName
    }

}