package ru.kamanin.nstu.graduate.thesis.shared.ticket.data.dto

import com.squareup.moshi.JsonClass
import ru.kamanin.nstu.graduate.thesis.shared.ticket.domain.entity.Status
import ru.kamanin.nstu.graduate.thesis.shared.ticket.domain.entity.TaskType

@JsonClass(generateAdapter = true)
data class TaskDto(
	val id: Long,
	val number: Int,
	val rating: Int?,
	val task: TaskDescription,
	val status: Status?
) {

	@JsonClass(generateAdapter = true)
	data class TaskDescription(
		val id: Long,
		val text: String,
		val themeName: String,
		val taskType: TaskType
	)
}