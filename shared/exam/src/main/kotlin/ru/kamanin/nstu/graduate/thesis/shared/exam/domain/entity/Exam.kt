package ru.kamanin.nstu.graduate.thesis.shared.exam.domain.entity

data class Exam(
	val id: Long,
	val name: String,
	val mark: Int,
	val dateTime: String,
	val allowed: Boolean
)