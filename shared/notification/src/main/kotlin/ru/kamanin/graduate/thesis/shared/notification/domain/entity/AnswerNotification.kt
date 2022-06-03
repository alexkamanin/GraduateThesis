package ru.kamanin.graduate.thesis.shared.notification.domain.entity

import com.squareup.moshi.JsonClass
import ru.kamanin.nstu.graduate.thesis.shared.ticket.domain.entity.TaskState

@JsonClass(generateAdapter = true)
data class AnswerNotification(
	val id: Long,
	val taskId: Long,
	val number: Int,
	val rating: Int,
	val studentRatingId: Long,
	val state: TaskState
)