package com.tsu.pring.data.rest

import android.util.Log
import com.tsu.pring.data.model.CoinGet
import com.tsu.pring.features.common.COIN_LIST_DETERMINANT
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class RestWorker {

    private suspend fun syncCoinsList(rest: ApiCoin): List<CoinGet> {
        return rest.checkResponse(rest.api.getCoins())
    }

    suspend fun <T> doWork(coinFunDeterminant: String): Result<List<CoinGet>?> = withContext(Dispatchers.IO) {
        try {

            val request = when (coinFunDeterminant) {
                COIN_LIST_DETERMINANT -> syncCoinsList(ApiCoin(SERVER_BASE_URL))
                else -> { null }
            }

            return@withContext  Result.success(request)
        } catch (e: Exception) {
            Log.d("RequestException", "exception: ${e.message}")
            Result.failure(e)
        }
    }

}