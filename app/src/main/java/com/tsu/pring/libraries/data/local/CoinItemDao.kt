package com.tsu.pring.libraries.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CoinItemDao {

	@Query("SELECT * FROM coin_items")
	fun getAllCoinItems(): List<CoinEntity>

	@Insert(onConflict = OnConflictStrategy.IGNORE)
	fun insertCoinItems(coinEntities: CoinEntity)

	@Query("SELECT * FROM tcoin_items")
	fun getAllTrendingCoinItems(): List<TrendingCoinEntity>

	@Insert(onConflict = OnConflictStrategy.IGNORE)
	fun insertTrendingCoinItems(coinEntities: TrendingCoinEntity)
}