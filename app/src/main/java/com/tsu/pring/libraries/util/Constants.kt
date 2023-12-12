package com.tsu.pring.libraries.util

object Constants {
    const val BASE_URL = "https://api.coingecko.com/api/v3/"

    const val COIN_LIST_URL = "coins/markets?vs_currency=usd&order=market_cap_desc&per_page=100&page=1&sparkline=false&price_change_percentage=1h"

    const val TRENDING_URL = "search/trending"

    const val MARKETS_URL = "exchanges"

    const val MARKET_CHARTS = "coins/{id}/market_chart"

    const val COIN_DETAIL = "coins/{id}"

    const val SEARCH = "search"
}
