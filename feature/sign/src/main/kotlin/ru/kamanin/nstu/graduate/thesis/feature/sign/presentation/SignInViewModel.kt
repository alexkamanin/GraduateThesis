package ru.kamanin.nstu.graduate.thesis.feature.sign.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import ru.kamanin.nstu.graduate.thesis.feature.sign.domain.scenario.LoginScenario
import ru.kamanin.nstu.graduate.thesis.shared.validation.ValidationResult
import ru.kamanin.nstu.graduate.thesis.shared.validation.email.EmailValidator
import ru.kamanin.nstu.graduate.thesis.utils.coroutines.event.EventDispatcher
import ru.kamanin.nstu.graduate.thesis.utils.coroutines.exception.launch
import ru.kamanin.nstu.graduate.thesis.utils.coroutines.flow.*
import ru.kamanin.nstu.graduate.thesis.utils.error.ErrorConverter
import ru.kamanin.nstu.graduate.thesis.utils.error.ErrorState
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
	private val loginScenario: LoginScenario,
	private val emailValidator: EmailValidator,
	private val errorConverter: ErrorConverter
) : ViewModel() {

	private companion object {

		const val EMPTY_TEXT = ""
	}

	private val _state = MutableStateFlow(SignInState())
	val state: StateFlow<SignInState> get() = _state.asStateFlow()

	private val _errorEvent = MutableLiveState<ErrorState>()
	val errorEvent: LiveState<ErrorState> get() = _errorEvent.asLiveState()

	val email = MutableStateFlow(EMPTY_TEXT)
	val password = MutableStateFlow(EMPTY_TEXT)

	val eventDispatcher = EventDispatcher<EventListener>()

	interface EventListener {

		fun navigateToSignUp()
		fun navigateToExamsList()
	}

	init {
		initRegistrationAvailableObserver()
	}

	private fun initRegistrationAvailableObserver() {
		combine(email, password) { emailQuery, passwordQuery -> emailQuery.isNotEmpty() && passwordQuery.isNotEmpty() }
			.onEach { available -> _state.value = _state.value.copy(signAvailable = available) }
			.launchIn(viewModelScope)
	}

	fun signIn() {
		verifyAllFields(onSuccess = ::handleValidationSuccess, onError = ::handleValidationError)
	}

	private fun verifyAllFields(
		onSuccess: () -> Unit,
		onError: (emailValidationResult: ValidationResult) -> Unit
	) {
		val emailValidationResult = emailValidator.validate(email.value)

		if (emailValidationResult.isValid()) {
			onSuccess()
		} else {
			onError(emailValidationResult)
		}
	}

	private fun handleValidationSuccess() {
		_state.value = _state.value.copy(
			signAvailable = false,
			registrationAvailable = false,
			signProcessAvailable = true
		)

		viewModelScope.launch(::handleError) {
			loginScenario(username = email.value, password = password.value)
			eventDispatcher.dispatchEvent { navigateToExamsList() }
		}
	}

	private fun handleError(throwable: Throwable) {
		_state.value = _state.value.copy(
			signAvailable = true,
			registrationAvailable = true,
			signProcessAvailable = false
		)

		val error = errorConverter.convert(throwable)
		_errorEvent(error)
	}

	private fun handleValidationError(emailValidationResult: ValidationResult) {
		_state.value = _state.value.copy(emailValidationResult = emailValidationResult)
		email.observe(::updateEmail, viewModelScope)
	}

	private fun updateEmail(emailQuery: String) {
		_state.value = _state.value.copy(emailValidationResult = emailValidator.validate(emailQuery))
	}

	fun goToSignUp() {
		eventDispatcher.dispatchEvent { navigateToSignUp() }
	}
}