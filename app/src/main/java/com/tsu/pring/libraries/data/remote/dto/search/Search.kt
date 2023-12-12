package com.tsu.pring.libraries.data.remote.dto.search

import com.tsu.pring.libraries.data.remote.dto.search.CoinSearchResponse

data class Search(
	val coins: List<CoinSearchResponse>,
    //val exchanges: List<ExchangeSearchResponse>
)

//data class ExchangeSearchResponse(
//    val id: String,
//    val name: String,
//    @SerializedName("large")
//    val imageUrl: String
//)