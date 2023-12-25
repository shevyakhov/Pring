package com.tsu.pring.features.home.coinlist

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tsu.pring.libraries.data.local.CoinItemRepository
import com.tsu.pring.libraries.data.remote.dto.coins.CoinItem
import com.tsu.pring.libraries.domain.repository.CoinRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CoinListViewModel (
    private val repository: CoinRepository,
    private val localRepository: CoinItemRepository,
): ViewModel() {

    val coinList = MutableStateFlow<List<CoinItem>>(emptyList())
    val filterList = MutableStateFlow<List<CoinItem>>(emptyList())

    init {
        viewModelScope.launch {
            coinList.update {
                localRepository.getAllCoinItems().take(300)
            }
        }
        Log.i(TAG, "coinList: ${coinList.value}")
    }

    fun search(text: String?) {
        if (!text.isNullOrEmpty()) {
            filterList.update {
                coinList.value.filter {
                    text.lowercase() in (it.name?.lowercase() ?: "") || text.lowercase() in (it.symbol?.lowercase() ?: "")
                }
            }
        }
    }

    companion object {
        val TAG = CoinListViewModel::class.java.simpleName
    }
}