package com.tsu.pring.data.rest

import com.tsu.pring.data.model.CoinGet
import com.tsu.pring.libraries.data.remote.dto.coins.CoinItem
import com.tsu.pring.libraries.util.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface CoinRestInterface {

	// Получение списка криптовалют
	@GET(Constants.COIN_LIST_URL)
	suspend fun getCoinList(): List<CoinItem>

	@GET(COIN_DESCRIPTION_PATH)
	suspend fun getCoin(
		@Path("id") coinId: String,
	): Response<List<CoinGet>>
}