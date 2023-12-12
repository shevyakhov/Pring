package com.tsu.pring.libraries.modules

import com.tsu.pring.features.stats.presentation.StatsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val statsModule = module {
	viewModel {
		StatsViewModel(
			repository = get(),
			router = get()
		)
	}
}