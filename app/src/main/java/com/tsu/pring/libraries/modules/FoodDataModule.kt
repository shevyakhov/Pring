package com.tsu.pring.libraries.modules

import androidx.room.Room
import com.tsu.pring.libraries.data.local.CoinItemDataSource
import com.tsu.pring.libraries.data.local.CoinItemRepository
import com.tsu.pring.libraries.data.local.DataBase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val DataModule = module {

	single {
		Room.databaseBuilder(androidContext(), DataBase::class.java, DataBase.name)
			.fallbackToDestructiveMigration()
			.build()
	}
	single { get<DataBase>().CoinItemDao() }

	single { CoinItemDataSource(get()) }
	factory { CoinItemRepository(get()) }

}