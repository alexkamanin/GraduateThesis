package ru.kamanin.nstu.graduate.thesis.shared.exam.data.dto

import com.squareup.moshi.JsonClass
import ru.kamanin.nstu.graduate.thesis.shared.exam.domain.entity.TaskType

@JsonClass(generateAdapter = true)
data class AnswerDto(
	val id: Long,
	val task: TaskDto
) {

	@JsonClass(generateAdapter = true)
	data class TaskDto(
		val id: Long,
		val cost: Int,
		val text: String,
		val theme: ThemeDto,
		val taskType: TaskType
	) {

		@JsonClass(generateAdapter = true)
		data class ThemeDto(
			val id: Long,
			val name: String
		)
	}
}