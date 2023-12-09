package com.tsu.pring.features.stats.presentation

import com.tsu.pring.libraries.data.remote.dto.coins.CoinDetail
import com.tsu.pring.libraries.domain.model.CoinChartTimeSpan

data class DetailState(
    val priceList: List<List<Double>> = emptyList(),
    val timeRange: CoinChartTimeSpan = CoinChartTimeSpan.TIMESPAN_7DAYS,
    val coinDetail: CoinDetail? = null
)