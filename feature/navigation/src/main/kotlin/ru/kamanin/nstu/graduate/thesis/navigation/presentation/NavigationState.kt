package ru.kamanin.nstu.graduate.thesis.navigation.presentation

import ru.kamanin.nstu.graduate.thesis.component.core.error.ErrorState

sealed interface NavigationState {

	object Initial : NavigationState

	object Loading : NavigationState

	data class Error(val errorState: ErrorState) : NavigationState
}