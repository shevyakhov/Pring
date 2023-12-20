package com.tsu.pring.libraries.data.local

import com.tsu.pring.libraries.data.remote.dto.coins.CoinItem

class CoinItemRepository(private val coinItemDataSource: CoinItemDataSource) {

	suspend fun getAllCoinItems(): List<CoinItem> {
		return coinItemDataSource.getAllCoinItems()
	}

	suspend fun insertCoinItems(coinItems: List<CoinItem>) {
		coinItemDataSource.insertCoinItems(coinItems)
	}
}