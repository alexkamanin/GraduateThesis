package ru.kamanin.nstu.graduate.thesis.shared.exam.domain.entity

import ru.kamanin.nstu.graduate.thesis.shared.account.domain.entity.Account
import java.io.Serializable

data class Exam(
	val id: Long,
	val name: String,
	val mark: Int,
	val period: Period,
	val allowed: Boolean,
	val teacher: Teacher,
	val regulationRating: RegulationRating
) : Serializable {

	data class Period(
		val id: Long,
		val start: Long,
		val end: Long,
		val state: PeriodState
	) : Serializable

	data class Teacher(
		val id: Long,
		val account: Account
	) : Serializable

	data class RegulationRating(
		val maxQuestionRating: Int,
		val maxExerciseRating: Int
	) : Serializable
}