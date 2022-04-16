package ru.kamanin.nstu.graduate.thesis.shared.exam.data.dto

import com.squareup.moshi.JsonClass
import ru.kamanin.nstu.graduate.thesis.shared.exam.domain.entity.Status
import ru.kamanin.nstu.graduate.thesis.shared.exam.domain.entity.TaskType

@JsonClass(generateAdapter = true)
data class AnswerDto(
	val id: Long,
	val number: Int,
	val rating: Int?,
	val task: TaskDto,
	val status: Status?
) {

	@JsonClass(generateAdapter = true)
	data class TaskDto(
		val id: Long,
		val text: String,
		val themeName: String,
		val taskType: TaskType
	)
}