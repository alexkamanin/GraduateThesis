package ru.kamanin.nstu.graduate.thesis.shared.exam.data.dto

import com.squareup.moshi.JsonClass
import ru.kamanin.nstu.graduate.thesis.shared.account.data.dto.AccountDto
import ru.kamanin.nstu.graduate.thesis.shared.exam.domain.entity.ExamState
import ru.kamanin.nstu.graduate.thesis.shared.exam.domain.entity.RatingState

@JsonClass(generateAdapter = true)
data class ExamDto(
	val id: Long,
	val semesterRating: Int,
	val questionRating: Int,
	val exerciseRating: Int,
	val groupRatingId: Long,
	val studentRatingState: RatingState,
	val exam: ExamInfo,
	val examRule: ExamRule,
	val teacher: Teacher,
) {

	@JsonClass(generateAdapter = true)
	data class ExamRule(
		val id: Long,
		val name: String,
		val duration: Long,
		val minimalSemesterRating: Int,
		val minimalExamRating: Int,
		val maximumExamRating: Int,
		val singleQuestionDefaultRating: Int,
		val singleExerciseDefaultRating: Int,
		val questionsRatingSum: Int,
		val exercisesRatingSum: Int,
		val disciplineId: Long,
	)

	@JsonClass(generateAdapter = true)
	data class ExamInfo(
		val id: Long,
		val name: String,
		val disciplineId: Long,
		val groupId: Long,
		val oneGroup: Boolean,
		val start: Long,
		val end: Long,
		val state: ExamState
	)

	@JsonClass(generateAdapter = true)
	data class Teacher(
		val id: Long,
		val account: AccountDto
	)
}