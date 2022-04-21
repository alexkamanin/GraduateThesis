package ru.kamanin.nstu.graduate.thesis.shared.exam.data.dto

import com.squareup.moshi.JsonClass
import ru.kamanin.nstu.graduate.thesis.shared.account.data.dto.AccountDto
import ru.kamanin.nstu.graduate.thesis.shared.exam.domain.entity.PeriodState

@JsonClass(generateAdapter = true)
data class ExamDto(
	val id: Long,
	val disciplineName: String,
	val semesterRating: Int,
	val examRating: Int,
	val allowed: Boolean,
	val teacher: Teacher,
	val examPeriod: Period,
	val maxQuestionRating: Int,
	val maxExerciseRating: Int
) {

	@JsonClass(generateAdapter = true)
	data class Teacher(
		val id: Long,
		val account: AccountDto
	)

	@JsonClass(generateAdapter = true)
	data class Period(
		val id: Long,
		val start: Long,
		val end: Long,
		val state: PeriodState
	)
}