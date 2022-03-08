package ru.kamanin.nstu.graduate.thesis.shared.exam.domain.entity

data class Answer(
	val id: Long,
	val cost: Int,
	val description: String,
	val theme: String,
	val taskType: TaskType
)