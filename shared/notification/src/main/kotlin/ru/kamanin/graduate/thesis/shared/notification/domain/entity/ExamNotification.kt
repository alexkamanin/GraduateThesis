package ru.kamanin.graduate.thesis.shared.notification.domain.entity

import com.squareup.moshi.JsonClass
import ru.kamanin.nstu.graduate.thesis.shared.exam.domain.entity.ExamState

@JsonClass(generateAdapter = true)
data class ExamNotification(
	val id: Long,
	val name: String,
	val disciplineId: Long,
	val groupId: Long,
	val oneGroup: Boolean,
	val start: Long,
	val end: Long,
	val state: ExamState
)