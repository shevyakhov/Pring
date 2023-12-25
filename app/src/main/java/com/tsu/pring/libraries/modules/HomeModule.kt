package com.tsu.pring.libraries.modules

import com.tsu.pring.features.home.presentation.HomeViewModel
import com.tsu.pring.features.home.presentation.ListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val HomeModule = module {
	viewModel {
		HomeViewModel(
			router = get(),
			repository = get(),
			localRepository = get()
		)
	}
	viewModel {
		ListViewModel(
			router = get(),
			repository = get(),
			localRepository = get()
		)
	}
}