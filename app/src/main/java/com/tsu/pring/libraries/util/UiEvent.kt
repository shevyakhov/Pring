package com.tsu.pring.libraries.util

sealed class UiEvent {
	data class ShowMessage(val message: String) : UiEvent()
}