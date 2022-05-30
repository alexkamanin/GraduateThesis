package ru.kamanin.nstu.graduate.thesis.shared.exam.domain.entity

import ru.kamanin.nstu.graduate.thesis.shared.account.domain.entity.Account
import java.io.Serializable

data class Exam(
	val id: Long,
	val name: String,
	val mark: Int,
	val period: Period,
	val teacher: Teacher,
	val regulationRating: RegulationRating,
	val examState: ExamState,
	val ratingState: RatingState
) : Serializable {

	data class Period(
		val start: Long,
		val end: Long,
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