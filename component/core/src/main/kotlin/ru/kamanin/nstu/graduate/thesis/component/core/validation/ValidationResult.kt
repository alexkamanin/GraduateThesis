package ru.kamanin.nstu.graduate.thesis.component.core.validation

enum class ValidationResult {
	VALID,
	INVALID,
	NONE;

	fun isValid() = this == VALID
}