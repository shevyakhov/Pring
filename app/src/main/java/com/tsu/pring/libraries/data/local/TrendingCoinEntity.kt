package com.tsu.pring.libraries.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tcoin_items")
data class TrendingCoinEntity(
	@ColumnInfo(name = "id") val id: String,
	@ColumnInfo(name = "coinId") val coinId: Int,
	@PrimaryKey val name: String,
	@ColumnInfo(name = "symbol") val symbol: String,
	@ColumnInfo(name = "marketCapRank") val marketCapRank: Int,
	@ColumnInfo(name = "thumb") val thumb: String,
	@ColumnInfo(name = "small") val small: String,
	@ColumnInfo(name = "large") val large: String,
	@ColumnInfo(name = "slug") val slug: String,
	@ColumnInfo(name = "priceBtc") val priceBtc: Double,
	@ColumnInfo(name = "score") val score: Int,
)