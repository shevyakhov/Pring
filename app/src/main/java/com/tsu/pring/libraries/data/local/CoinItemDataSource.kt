package com.tsu.pring.libraries.data.local

import com.tsu.pring.libraries.data.remote.dto.coins.CoinItem
import com.tsu.pring.libraries.data.remote.dto.coins.TrendingCoinItem
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
	suspend fun getAllTrendingCoinItems(): List<TrendingCoinItem> = withContext(Dispatchers.IO) {
		coinItemDao.getAllTrendingCoinItems().map { it.toCoinItem() }
	}

	suspend fun insertTrendingCoinItems(coinItems: List<TrendingCoinItem>) = withContext(Dispatchers.IO) {
		coinItems.forEach {
			coinItemDao.insertTrendingCoinItems(it.toEntity())
		}
	}
}