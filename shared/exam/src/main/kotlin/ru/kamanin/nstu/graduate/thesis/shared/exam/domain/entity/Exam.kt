package ru.kamanin.nstu.graduate.thesis.shared.exam.domain.entity

import java.io.Serializable

data class Exam(
	val id: Long,
	val name: String,
	val mark: Int,
	val period: Period,
	val allowed: Boolean,
	val teacher: Teacher,
) {

	data class Period(
		val id: Long,
		val start: Long,
		val end: Long,
	) : Serializable

	data class Teacher(
		val id: Long,
		val username: String,
		val name: String,
		val surname: String
	)
}