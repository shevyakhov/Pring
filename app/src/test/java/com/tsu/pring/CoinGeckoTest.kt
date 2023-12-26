package com.tsu.pring

import com.tsu.pring.data.rest.ApiCoin
import com.tsu.pring.libraries.domain.repository.CoinRepository
import com.tsu.pring.libraries.util.Constants.BASE_URL
import io.mockk.junit4.MockKRule
import io.mockk.mockk
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals


class CoinGeckoApi {

    @JvmField
    @Rule
    val rule = MockKRule(this)

    private val coinRepository: CoinRepository = mockk(relaxed = true)

    @Test
    fun `compare data from database and get request result`() = runBlocking {
        val rest = ApiCoin(BASE_URL)
        val response = rest.api.getCoinList()
        val coinListFromDb = coinRepository.getCoins().map { it.data }.filterNotNull().single()
        assertEquals(response, coinListFromDb)
    }
}
