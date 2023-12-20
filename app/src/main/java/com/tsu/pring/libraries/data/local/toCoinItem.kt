package com.tsu.pring.libraries.data.local

import com.tsu.pring.libraries.data.remote.dto.coins.CoinItem

fun CoinEntity.toCoinItem(): CoinItem =
	CoinItem(
		id,
		symbol,
		name,
		image,
		currentPrice,
		marketCap,
		marketCapRank,
		fullyDilutedValuation,
		totalVolume,
		high24h,
		low24h,
		priceChange24h,
		priceChangePercentage24h,
		marketCapChange24h,
		marketCapChangePercentage24h,
		circulatingSupply,
		totalSupply,
		maxSupply,
		ath,
		athChangePercentage,
		athDate,
		atl,
		atlChangePercentage,
		atlDate,
		roi,
		lastUpdated,
		priceChangePercentage1hInCurrency
	)

fun CoinItem.toEntity(): CoinEntity =
	CoinEntity(
		id,
		symbol,
		name.toString(),
		image,
		currentPrice,
		marketCap,
		marketCapRank,
		fullyDilutedValuation,
		totalVolume,
		high24h,
		low24h,
		priceChange24h,
		priceChangePercentage24h,
		marketCapChange24h,
		marketCapChangePercentage24h,
		circulatingSupply,
		totalSupply,
		maxSupply,
		ath,
		athChangePercentage,
		athDate,
		atl,
		atlChangePercentage,
		atlDate,
		roi,
		lastUpdated,
		priceChangePercentage1hInCurrency
	)