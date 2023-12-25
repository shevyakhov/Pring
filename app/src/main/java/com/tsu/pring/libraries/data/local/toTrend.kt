package com.tsu.pring.libraries.data.local

import com.tsu.pring.libraries.data.remote.dto.coins.TrendingCoinItem

fun TrendingCoinEntity.toCoinItem(): TrendingCoinItem =
	TrendingCoinItem(
		id, coinId, name, symbol, marketCapRank, thumb, small, large, slug, priceBtc, score
	)

fun TrendingCoinItem.toEntity(): TrendingCoinEntity =
	TrendingCoinEntity(
		id, coinId, name, symbol, marketCapRank, thumb, small, large, slug, priceBtc, score
	)