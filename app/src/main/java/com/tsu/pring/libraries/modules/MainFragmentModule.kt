package com.tsu.pring.libraries.modules

import com.tsu.pring.application.mainFragment.presentation.MainFragmentViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val MainFragmentModule = module {
	viewModel {
		MainFragmentViewModel(
			navigator = get()
		)
	}
}