package ru.kamanin.nstu.graduate.thesis.feature.sign.domain.validation

import ru.kamanin.nstu.graduate.thesis.component.core.validation.ValidationResult
import ru.kamanin.nstu.graduate.thesis.component.core.validation.Validator

class EmailValidator : Validator<String> {

	private companion object {

		val EMAIL_REGEX = "([A-Za-z0-9]{1,}[\\\\.-]{0,1}[A-Za-z0-9]{1,})+@([A-Za-z0-9]{1,}[\\\\.-]{0,1}[A-Za-z0-9]{1,})+[\\\\.]{1}[a-z]{2,4}".toRegex()
	}

	override fun validate(value: String): ValidationResult =
		when {
			value.isEmpty()            -> ValidationResult.NONE
			value.matches(EMAIL_REGEX) -> ValidationResult.VALID
			else                       -> ValidationResult.INVALID
		}
}