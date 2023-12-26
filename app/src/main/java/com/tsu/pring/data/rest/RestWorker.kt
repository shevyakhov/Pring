package com.tsu.pring.data.rest

import android.util.Log
import com.tsu.pring.features.common.COIN_LIST_DETERMINANT
import com.tsu.pring.libraries.data.remote.dto.coins.CoinItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RestWorker {

    private suspend fun syncCoinsList(rest: ApiCoin): List<CoinItem> {
        return rest.api.getCoinList()
    }

    suspend fun <T> doWork(coinFunDeterminant: String): Result<List<CoinItem>?> = withContext(Dispatchers.IO) {
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