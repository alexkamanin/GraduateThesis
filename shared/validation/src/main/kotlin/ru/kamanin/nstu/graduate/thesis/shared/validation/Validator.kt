package ru.kamanin.nstu.graduate.thesis.shared.validation

interface Validator<T> {

	fun validate(value: T): ValidationResult
}