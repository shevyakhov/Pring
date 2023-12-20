package com.tsu.pring.libraries.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.tsu.pring.libraries.data.remote.dto.coins.Roi

@Entity(tableName = "coin_items")
data class CoinEntity(
	@ColumnInfo(name = "id") val id: String?,
	@ColumnInfo(name = "symbol") val symbol: String?,
	@PrimaryKey() val name: String,
	@ColumnInfo(name = "image") val image: String? = null,
	@ColumnInfo(name = "currentPrice") val currentPrice: Double = 0.0,
	@ColumnInfo(name = "marketCap") val marketCap: Double = 0.0,
	@ColumnInfo(name = "marketCapRank") val marketCapRank: Long = 0,
	@ColumnInfo(name = "fullyDilutedValuation") val fullyDilutedValuation: Double?,
	@ColumnInfo(name = "totalVolume") val totalVolume: Double = 0.0,
	@ColumnInfo(name = "high24h") val high24h: Double = 0.0,
	@ColumnInfo(name = "low24h") val low24h: Double = 0.0,
	@ColumnInfo(name = "priceChange24h") val priceChange24h: Double = 0.0,
	@ColumnInfo(name = "priceChangePercentage24h") val priceChangePercentage24h: Double = 0.0,
	@ColumnInfo(name = "marketCapChange24h") val marketCapChange24h: Double = 0.0,
	@ColumnInfo(name = "marketCapChangePercentage24h") val marketCapChangePercentage24h: Double = 0.0,
	@ColumnInfo(name = "circulatingSupply") val circulatingSupply: Double = 0.0,
	@ColumnInfo(name = "totalSupply") val totalSupply: Double? = null,
	@ColumnInfo(name = "maxSupply") val maxSupply: Double? = null,
	@ColumnInfo(name = "ath") val ath: Double = 0.0,
	@ColumnInfo(name = "athChangePercentage") val athChangePercentage: Double = 0.0,
	@ColumnInfo(name = "athDate") val athDate: String? = null,
	@ColumnInfo(name = "atl") val atl: Double = 0.0,
	@ColumnInfo(name = "atlChangePercentage") val atlChangePercentage: Double = 0.0,
	@ColumnInfo(name = "atlDate") val atlDate: String? = null,
	@ColumnInfo(name = "roi") val roi: Roi? = null,
	@ColumnInfo(name = "lastUpdated") val lastUpdated: String? = null,
	@ColumnInfo(name = "priceChangePercentage1hInCurrency") val priceChangePercentage1hInCurrency: Double = 0.0,
)
