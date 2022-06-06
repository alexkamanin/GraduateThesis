package ru.kamanin.nstu.graduate.thesis.feature.sign.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import ru.kamanin.nstu.graduate.thesis.component.test.coroutines.TestCoroutineRule
import ru.kamanin.nstu.graduate.thesis.feature.sign.domain.scenario.LoginScenario
import ru.kamanin.nstu.graduate.thesis.shared.validation.ValidationResult
import ru.kamanin.nstu.graduate.thesis.shared.validation.email.EmailValidator
import ru.kamanin.nstu.graduate.thesis.utils.error.ErrorConverter

@RunWith(MockitoJUnitRunner::class)
@OptIn(ExperimentalCoroutinesApi::class)
class SingInViewModelTest {

	@get:Rule
	val taskExecutorRule = InstantTaskExecutorRule()

	@get:Rule
	val testCoroutineRule = TestCoroutineRule()

	private val loginScenario: LoginScenario = mock()
	private val emailValidator: EmailValidator = mock()
	private val errorConverter: ErrorConverter = mock()

	private val viewModel by lazy { SignInViewModel(loginScenario, emailValidator, errorConverter) }

	private val emailValue = "sample@stud.nstu.ru"
	private val passwordValue = "12345678"

	@Test
	fun `sign in EXPECT validate email`() = runTest {
		whenever(emailValidator.validate(emailValue)).thenReturn(ValidationResult.VALID)

		viewModel.email.value = emailValue
		viewModel.password.value = passwordValue
		viewModel.signIn()

		verify(emailValidator).validate(emailValue)
	}

	@Test
	fun `sign in with correct email EXPECT invoke login scenario`() = runTest {
		whenever(emailValidator.validate(emailValue)).thenReturn(ValidationResult.VALID)

		viewModel.email.value = emailValue
		viewModel.password.value = passwordValue
		viewModel.signIn()

		verify(loginScenario).invoke(emailValue, passwordValue)
	}

	@Test
	fun `sign in with incorrect email EXPECT not invoke login scenario`() = runTest {
		whenever(emailValidator.validate(emailValue)).thenReturn(ValidationResult.INVALID)

		viewModel.email.value = emailValue
		viewModel.password.value = passwordValue
		viewModel.signIn()

		verify(loginScenario, never()).invoke(emailValue, passwordValue)
	}

	@Test
	fun `view model created EXPECT sign in unavailable and sign up available`() = runTest {
		val expectedState = SignInState(
			signAvailable = false,
			registrationAvailable = true,
		)

		viewModel.state.test {
			assertEquals(expectedState, awaitItem())
			cancelAndIgnoreRemainingEvents()
		}
	}

	@Test
	fun `login with valid email and not empty password EXPECT login available and registration unavailable`() = runTest {
		val expectedState = SignInState(
			emailValidationResult = ValidationResult.NONE,
			signAvailable = false,
			registrationAvailable = false,
			signProcessAvailable = true
		)
		whenever(emailValidator.validate(emailValue)).thenReturn(ValidationResult.VALID)

		viewModel.email.value = emailValue
		viewModel.password.value = passwordValue
		viewModel.signIn()

		viewModel.state.test {
			assertEquals(expectedState, awaitItem())
			cancelAndIgnoreRemainingEvents()
		}
	}

	@Test
	fun `login with invalid email and not empty password EXPECT login unavailable and registration available`() = runTest {
		val expectedState = SignInState(emailValidationResult = ValidationResult.INVALID, signAvailable = false, registrationAvailable = true)
		whenever(emailValidator.validate(emailValue)).thenReturn(ValidationResult.INVALID)

		viewModel.email.value = emailValue
		viewModel.password.value = passwordValue
		viewModel.signIn()

		viewModel.state.test {
			assertEquals(expectedState, awaitItem())
			cancelAndIgnoreRemainingEvents()
		}
	}
}