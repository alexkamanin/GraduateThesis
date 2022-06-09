package ru.kamanin.nstu.graduate.thesis.navigation.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import ru.kamanin.graduate.thesis.shared.notification.domain.usecase.SetFirebaseNotificationTokenUseCase
import ru.kamanin.nstu.graduate.thesis.shared.session.domain.scenario.ExtendSessionScenario
import ru.kamanin.nstu.graduate.thesis.shared.session.domain.scenario.LogoutScenario
import ru.kamanin.nstu.graduate.thesis.shared.session.domain.usecase.GetSessionConfigUseCase
import ru.kamanin.nstu.graduate.thesis.utils.coroutines.event.EventDispatcher
import ru.kamanin.nstu.graduate.thesis.utils.coroutines.exception.launch
import ru.kamanin.nstu.graduate.thesis.utils.error.ErrorConverter
import ru.kamanin.nstu.graduate.thesis.utils.error.ErrorState
import javax.inject.Inject

@HiltViewModel
class NavigationViewModel @Inject constructor(
	private val extendSessionScenario: ExtendSessionScenario,
	private val setFirebaseNotificationTokenUseCase: SetFirebaseNotificationTokenUseCase,
	private val getSessionConfigUseCase: GetSessionConfigUseCase,
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
			if (getSessionConfigUseCase().autoExtend) {
				extendSessionScenario()
				runCatching { setFirebaseNotificationTokenUseCase() }
				eventDispatcher.dispatchEvent { navigateToExamsList() }
			} else {
				logout()
			}
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