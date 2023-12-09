package com.tsu.pring.application

import android.app.Application
import com.tsu.pring.features.prediction.di.PredictionModule
import com.tsu.pring.libraries.modules.AppModule
import com.tsu.pring.libraries.modules.HomeModule
import com.tsu.pring.libraries.modules.MainFragmentModule
import com.tsu.pring.libraries.modules.RouterModule
import com.tsu.pring.libraries.modules.SplashModule
import com.tsu.pring.libraries.modules.statsModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {

	override fun onCreate() {
		super.onCreate()
		INSTANCE = this

		startKoin {
			androidContext(this@App)
			modules(AppModule)
			modules(MainFragmentModule)
			modules(RouterModule)
			modules(SplashModule)
			modules(HomeModule)
			modules(PredictionModule)
			modules(statsModule)
		}

	}

	companion object {

		internal lateinit var INSTANCE: App
			private set
	}
}