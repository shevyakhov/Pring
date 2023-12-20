package com.tsu.pring.data.rest

import com.tsu.pring.data.model.CoinGet
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface CoinRestInterface {

	// Получение списка криптовалют
	@GET(COINS_LIST_PATH)
	suspend fun getCoins(
		@Header("X-RapidAPI-Key") key: String = "CG-GUNmvAdKnYxjgexwQ3ugK2bw",
		@Header("X-RapidAPI-Host") host: String = "coingecko.p.rapidapi.com",
	): Response<List<CoinGet>>

	@GET(COIN_DESCRIPTION_PATH)
	suspend fun getCoin(
		@Path("id") coinId: String,
	): Response<List<CoinGet>>
}