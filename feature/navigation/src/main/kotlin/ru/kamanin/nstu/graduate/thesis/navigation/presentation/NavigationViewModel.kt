package ru.kamanin.nstu.graduate.thesis.navigation.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.kamanin.nstu.graduate.thesis.component.core.coroutines.exception.launch
import ru.kamanin.nstu.graduate.thesis.component.core.error.ErrorConverter
import ru.kamanin.nstu.graduate.thesis.component.core.error.ErrorState
import ru.kamanin.nstu.graduate.thesis.component.core.mvvm.lifecycle.EventDispatcher
import ru.kamanin.nstu.graduate.thesis.shared.session.domain.scenario.ExtendSessionScenario
import ru.kamanin.nstu.graduate.thesis.shared.session.domain.scenario.LogoutScenario
import javax.inject.Inject

@HiltViewModel
class NavigationViewModel @Inject constructor(
	private val extendSessionScenario: ExtendSessionScenario,
	private val logoutScenario: LogoutScenario,
	private val errorConverter: ErrorConverter
) : ViewModel() {

	private val _state = MutableStateFlow<NavigationState>(NavigationState.Initial)
	val state: StateFlow<NavigationState> get() = _state.asStateFlow()

	val eventDispatcher = EventDispatcher<EventListener>()

	interface EventListener {

		fun navigateToSignIn()
		fun navigateToExamsList()
	}

	init {
		_state.value = NavigationState.Loading
		extendSession()
	}

	fun repeat() {
		extendSession()
	}

	private fun extendSession() {
		viewModelScope.launch(::handleError) {
			extendSessionScenario()
			eventDispatcher.dispatchEvent { navigateToExamsList() }
		}
	}

	private fun handleError(throwable: Throwable) {
		errorConverter.convert(throwable).let { errorState ->
			when (errorState) {
				ErrorState.LostConnection,
				ErrorState.ServiceUnavailable -> _state.value = NavigationState.Error(errorState)

				else                          -> logout()
			}
		}
	}

	private fun logout() {
		viewModelScope.launch {
			logoutScenario()
			eventDispatcher.dispatchEvent { navigateToSignIn() }
		}
	}
}