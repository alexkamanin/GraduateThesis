package ru.kamanin.nstu.graduate.thesis.feature.sign.presentation

import ru.kamanin.nstu.graduate.thesis.component.core.validation.ValidationResult

data class SignUpState(
	val emailValidationResult: ValidationResult = ValidationResult.NONE,
	val passwordMatch: Boolean = true,
	val registrationAvailable: Boolean = false,
	val registrationProcessAvailable: Boolean = false,
)