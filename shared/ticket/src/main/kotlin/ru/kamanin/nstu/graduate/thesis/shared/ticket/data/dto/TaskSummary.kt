package ru.kamanin.nstu.graduate.thesis.shared.ticket.data.dto

import ru.kamanin.nstu.graduate.thesis.shared.ticket.domain.entity.TaskType

data class TaskSummary(
	val id: Long,
	val text: String?,
	val artefactId: Long?,
	val taskType: TaskType,
	val themeId: Long
)