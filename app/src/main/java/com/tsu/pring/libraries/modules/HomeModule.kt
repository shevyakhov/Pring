package com.tsu.pring.libraries.modules

import com.tsu.pring.features.home.presentation.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.scope.get
import org.koin.dsl.module

val HomeModule = module {
	viewModel {
		HomeViewModel(
			router = get(),
			restWorker = get(),
		)
	}
}