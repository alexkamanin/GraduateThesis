package ru.kamanin.nstu.graduate.thesis.component.core.validation

interface Validator<T> {

	fun validate(value: T): ValidationResult
}