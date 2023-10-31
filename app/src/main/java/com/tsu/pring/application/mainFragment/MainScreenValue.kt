package com.tsu.pring.application.mainFragment

sealed class MainScreenValue {

	data object Home : MainScreenValue()
	data object Stats : MainScreenValue()
	data object Prediction : MainScreenValue()
}