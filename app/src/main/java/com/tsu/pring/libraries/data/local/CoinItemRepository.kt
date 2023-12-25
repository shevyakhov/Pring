package com.tsu.pring.libraries.data.local

import com.tsu.pring.libraries.data.remote.dto.coins.CoinItem
import com.tsu.pring.libraries.data.remote.dto.coins.TrendingCoinItem

class CoinItemRepository(private val coinItemDataSource: CoinItemDataSource) {

	suspend fun getAllCoinItems(): List<CoinItem> {
		return coinItemDataSource.getAllCoinItems()
	}

	suspend fun insertCoinItems(coinItems: List<CoinItem>) {
		coinItemDataSource.insertCoinItems(coinItems)
	}
	suspend fun getAllTrendingCoinItems(): List<TrendingCoinItem> {
		return coinItemDataSource.getAllTrendingCoinItems()
	}

	suspend fun insertTrendingCoinItems(coinItems: List<TrendingCoinItem>) {
		coinItemDataSource.insertTrendingCoinItems(coinItems)
	}
}