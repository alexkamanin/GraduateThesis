package ru.kamanin.nstu.graduate.thesis.shared.ticket.data.dto

import ru.kamanin.nstu.graduate.thesis.shared.ticket.domain.entity.TaskState

data class TaskStateModel(
	val id: Long,
	val state: TaskState
)