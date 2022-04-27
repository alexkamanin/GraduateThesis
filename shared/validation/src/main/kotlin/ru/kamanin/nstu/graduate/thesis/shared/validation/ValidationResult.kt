package ru.kamanin.nstu.graduate.thesis.shared.validation

enum class ValidationResult {
	VALID,
	INVALID,
	NONE;

	fun isValid() = this == VALID
}