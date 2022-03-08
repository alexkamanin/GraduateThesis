package ru.kamanin.nstu.graduate.thesis.feature.sign.presentation

import ru.kamanin.nstu.graduate.thesis.component.core.validation.ValidationResult

data class SignInState(
	val emailValidationResult: ValidationResult = ValidationResult.NONE,
	val signProcessAvailable: Boolean = false,
	val signAvailable: Boolean = false,
	val registrationAvailable: Boolean = true,
)