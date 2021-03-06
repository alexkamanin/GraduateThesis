package ru.kamanin.nstu.graduate.thesis.navigation.presentation

import ru.kamanin.nstu.graduate.thesis.utils.error.ErrorState

sealed interface NavigationState {

	object Initial : NavigationState

	object Loading : NavigationState

	data class Error(val errorState: ErrorState) : NavigationState
}