package ru.kamanin.nstu.graduate.thesis.shared.validation.email

import ru.kamanin.nstu.graduate.thesis.shared.validation.ValidationResult
import ru.kamanin.nstu.graduate.thesis.shared.validation.Validator
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EmailValidator @Inject constructor() : Validator<String> {

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