package com.tsu.pring.libraries.navigators

import kotlinx.coroutines.flow.StateFlow

interface MainRouter {

	val destinationStateFlow: StateFlow<FragmentDestination>

	fun open(destination: FragmentDestination)

	fun exit()
}