package com.tsu.pring.libraries.data.local

import com.tsu.pring.libraries.data.remote.dto.coins.CoinItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CoinItemDataSource(private val coinItemDao: CoinItemDao) {

	suspend fun getAllCoinItems(): List<CoinItem> = withContext(Dispatchers.IO) {
		coinItemDao.getAllCoinItems().map { it.toCoinItem() }
	}

	suspend fun insertCoinItems(coinItems: List<CoinItem>) = withContext(Dispatchers.IO) {
		coinItems.forEach {
			coinItemDao.insertCoinItems(it.toEntity())
		}
	}
}