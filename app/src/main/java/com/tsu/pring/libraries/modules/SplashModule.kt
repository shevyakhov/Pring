package com.tsu.pring.libraries.modules

import com.tsu.pring.features.splash.presentation.SplashViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val SplashModule = module {
	viewModel {
		SplashViewModel(
			router = get(),
		)
	}
}