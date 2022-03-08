package ru.kamanin.nstu.graduate.thesis.shared.exam.data.dto

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ExamDto(
	val id: Long,
	val semesterRating: Int,
	val examRating: Int,
	val allowed: Boolean,
	val examPeriod: ExamPeriod
) {

	@JsonClass(generateAdapter = true)
	data class ExamPeriod(
		val id: Long,
		val start: String,
		val end: String,
		val exam: Exam
	) {

		@JsonClass(generateAdapter = true)
		data class Exam(
			val id: Long,
			val examRule: ExamRule,
			val groups: List<Group>
		) {

			@JsonClass(generateAdapter = true)
			data class ExamRule(
				val id: Long,
				val themes: List<Theme>,
				val questionCount: Int,
				val exerciseCount: Int,
				val duration: Int,
				val minimalRating: Int,
				val discipline: Discipline
			) {

				@JsonClass(generateAdapter = true)
				data class Theme(
					val id: Long,
					val name: String,
					val discipline: Discipline
				)
			}

			@JsonClass(generateAdapter = true)
			data class Discipline(
				val id: Long,
				val name: String,
			)

			@JsonClass(generateAdapter = true)
			data class Group(
				val id: Long,
				val name: String
			)
		}
	}
}