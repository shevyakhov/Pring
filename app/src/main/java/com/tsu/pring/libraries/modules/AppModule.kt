package com.tsu.pring.libraries.modules

import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.Router
import com.tsu.pring.application.activity.presentation.MainActivityViewModel
import com.tsu.pring.data.rest.RestWorker
import com.tsu.pring.libraries.modules.GlobalNavigatorName.GLOBAL
import com.tsu.pring.libraries.modules.MainNavigatorName.MAIN
import com.tsu.pring.libraries.navigators.GlobalRouter
import com.tsu.pring.libraries.navigators.GlobalRouterImpl
import com.tsu.pring.libraries.navigators.MainRouter
import com.tsu.pring.libraries.navigators.MainRouterImpl
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

object GlobalNavigatorName {

	const val GLOBAL = "GLOBAL"
}

object MainNavigatorName {

	const val MAIN = "MAIN"
}

fun buildCicerone(): Cicerone<Router> = Cicerone.create()

val AppModule = module {

	single(named(GLOBAL)) { buildCicerone() }
	single(named(GLOBAL)) { get<Cicerone<Router>>(named(GLOBAL)).router }
	single(named(GLOBAL)) { get<Cicerone<Router>>(named(GLOBAL)).getNavigatorHolder() }
	single<GlobalRouter> { GlobalRouterImpl(get(named(GLOBAL))) }

	single(named(MAIN)) { buildCicerone() }
	single(named(MAIN)) { get<Cicerone<Router>>(named(MAIN)).router }
	single(named(MAIN)) { get<Cicerone<Router>>(named(MAIN)).getNavigatorHolder() }
	single<MainRouter> { MainRouterImpl(get(named(MAIN))) }

	single { RestWorker() }

	viewModel { MainActivityViewModel(navigator = get(), repository = get(), localRepository = get()) }
}