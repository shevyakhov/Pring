package com.tsu.pring.libraries.domain.repository

import com.tsu.pring.libraries.data.remote.dto.coins.CoinDetail
import com.tsu.pring.libraries.data.remote.dto.coins.CoinItem
import com.tsu.pring.libraries.data.remote.dto.coins.CoinMarketChart
import com.tsu.pring.libraries.data.remote.dto.coins.TrendingCoin
import com.tsu.pring.libraries.data.remote.dto.markets.Exchange
import com.tsu.pring.libraries.data.remote.dto.search.Search
import com.tsu.pring.libraries.util.Resource
import kotlinx.coroutines.flow.Flow

interface CoinRepository {

	fun getMarketList(): Flow<Resource<List<Exchange>>>
	fun getCoins(): Flow<Resource<List<CoinItem>>>
	fun getTrendingCoins(): Flow<Resource<List<TrendingCoin>>>

	fun getMarketChart(id: String, day: Int): Flow<Resource<CoinMarketChart>>
	suspend fun getMarketChartResult(id: String, day: Int): CoinMarketChart

	fun getCoinDetail(id: String): Flow<Resource<CoinDetail>>

	fun search(query: String): Flow<Resource<Search>>
}
