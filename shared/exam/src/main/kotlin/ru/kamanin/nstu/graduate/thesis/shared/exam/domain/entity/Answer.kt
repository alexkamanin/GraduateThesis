package ru.kamanin.nstu.graduate.thesis.shared.exam.domain.entity

import java.io.Serializable

data class Answer(
	val id: Long,
	val number: Int,
	val rating: Int?,
	val maxRating: Int,
	val description: String,
	val theme: String,
	val taskType: TaskType,
	val status: Status?
) : Serializable