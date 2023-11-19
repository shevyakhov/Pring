package com.tsu.pring.data.model

import com.google.gson.annotations.SerializedName

data class CoinGet(
    // - Код валюты на сервере
    @SerializedName("id")
    val id: String? = null,

    @SerializedName("symbol")
    val symbol: String? = null,

    @SerializedName("name")
    val name: String? = null,

    val image: String? = null,

    @SerializedName("current_price")
    val currentPrice: Double? = null,

    @SerializedName("price_change_percentage_24h")
    val priceChangePercent: Double? = null,
)
