package com.tsu.pring.libraries.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
	entities = [CoinEntity::class],
	version = 1,
	exportSchema = false
)
@TypeConverters(RoiTypeConverter::class)
abstract class DataBase : RoomDatabase() {

	companion object {

		const val name = "DB"
	}

	abstract fun CoinItemDao(): CoinItemDao
}