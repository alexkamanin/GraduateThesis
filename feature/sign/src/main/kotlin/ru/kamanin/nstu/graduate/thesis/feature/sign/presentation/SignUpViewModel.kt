package ru.kamanin.nstu.graduate.thesis.feature.sign.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import ru.kamanin.nstu.graduate.thesis.feature.sign.domain.scenario.LoginScenario
import ru.kamanin.nstu.graduate.thesis.shared.account.domain.usecase.ChangePasswordUseCase
import ru.kamanin.nstu.graduate.thesis.shared.validation.ValidationResult
import ru.kamanin.nstu.graduate.thesis.shared.validation.email.EmailValidator
import ru.kamanin.nstu.graduate.thesis.utils.coroutines.event.EventDispatcher
import ru.kamanin.nstu.graduate.thesis.utils.coroutines.exception.launch
import ru.kamanin.nstu.graduate.thesis.utils.coroutines.flow.*
import ru.kamanin.nstu.graduate.thesis.utils.error.ErrorConverter
import ru.kamanin.nstu.graduate.thesis.utils.error.ErrorState
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
	private val loginScenario: LoginScenario,
	private val changePasswordUseCase: ChangePasswordUseCase,
	private val emailValidator: EmailValidator,
	private val errorConverter: ErrorConverter
) : ViewModel() {

	private companion object {

		const val EMPTY_TEXT = ""
	}

	private val _state = MutableStateFlow(SignUpState())
	val state: StateFlow<SignUpState> get() = _state.asStateFlow()

	private val _errorEvent = MutableLiveState<ErrorState>()
	val errorEvent: LiveState<ErrorState> get() = _errorEvent.asLiveState()

	val email = MutableStateFlow(EMPTY_TEXT)
	val password = MutableStateFlow(EMPTY_TEXT)
	val repeatPassword = MutableStateFlow(EMPTY_TEXT)
	val verificationCode = MutableStateFlow(EMPTY_TEXT)

	val eventDispatcher = EventDispatcher<EventListener>()

	interface EventListener {

		fun navigateBack()
		fun navigateToExamsList()
	}

	init {
		initRegistrationAvailableObserver()
	}

	private fun initRegistrationAvailableObserver() {
		combine(email, password, repeatPassword, verificationCode) { emailQuery, passwordQuery, repeatPasswordQuery, verificationCodeQuery ->
			emailQuery.isNotEmpty()
				&& passwordQuery.isNotEmpty()
				&& repeatPasswordQuery.isNotEmpty()
				&& verificationCodeQuery.isNotEmpty()
		}
			.onEach { available -> _state.value = _state.value.copy(registrationAvailable = available) }
			.launchIn(viewModelScope)
	}

	fun signUp() {
		verifyAllField(onSuccess = ::handleValidationSuccess, onError = ::handleValidationError)
	}

	private fun verifyAllField(
		onSuccess: () -> Unit,
		onError: (emailValidationResult: ValidationResult, passwordMatchResult: Boolean) -> Unit
	) {
		val emailValidationResult = emailValidator.validate(email.value)
		val passwordMatchResult = password.value == repeatPassword.value

		if (emailValidationResult.isValid() && passwordMatchResult) {
			onSuccess()
		} else {
			onError(emailValidationResult, passwordMatchResult)
		}
	}

	private fun handleValidationSuccess() {
		_state.value = _state.value.copy(registrationAvailable = false, registrationProcessAvailable = true)

		viewModelScope.launch(::handleError) {
			changePasswordUseCase(
				username = email.value,
				password = password.value,
				verificationCode = verificationCode.value
			)
			loginScenario(username = email.value, password = password.value)
			eventDispatcher.dispatchEvent { navigateToExamsList() }
		}
	}

	private fun handleError(throwable: Throwable) {
		_state.value = _state.value.copy(registrationAvailable = true, registrationProcessAvailable = false)

		val error = errorConverter.convert(throwable)
		_errorEvent(error)
	}

	private fun handleValidationError(emailValidationResult: ValidationResult, passwordMatchResult: Boolean) {
		_state.value = _state.value.copy(
			emailValidationResult = emailValidationResult,
			passwordMatch = passwordMatchResult
		)
		email.observe(::updateEmail, viewModelScope)
		combine(password, repeatPassword, ::updatePassword).launchIn(viewModelScope)
	}

	private fun updateEmail(emailQuery: String) {
		_state.value = _state.value.copy(emailValidationResult = emailValidator.validate(emailQuery))
	}

	private fun updatePassword(passwordQuery: String, repeatPasswordQuery: String) {
		_state.value = _state.value.copy(passwordMatch = passwordQuery == repeatPasswordQuery)
	}

	fun goBack() {
		eventDispatcher.dispatchEvent { navigateBack() }
	}
}