package ru.kamanin.nstu.graduate.thesis.shared.ticket.domain.entity

import java.io.Serializable

data class Task(
	val id: Long,
	val number: Int,
	val rating: Int?,
	val maxRating: Int,
	val description: String,
	val theme: String,
	val taskType: TaskType,
	val status: Status?
) : Serializable