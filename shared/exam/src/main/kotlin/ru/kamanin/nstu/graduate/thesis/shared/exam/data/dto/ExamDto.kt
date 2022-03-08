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
		val start: Long,
		val end: Long,
		val exam: Exam
	) {

		@JsonClass(generateAdapter = true)
		data class Exam(
			val id: Long,
			val examRule: ExamRule,
			val teacher: Teacher,
			val groups: List<Group>
		) {

			@JsonClass(generateAdapter = true)
			data class Teacher(
				val id: Long,
				val account: Account
			)

			@JsonClass(generateAdapter = true)
			data class Account(
				val id: Long,
				val username: String,
				val name: String,
				val surname: String
			)

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