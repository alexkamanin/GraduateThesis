package ru.kamanin.nstu.graduate.thesis.feature.exam.ticket.ui.model

import ru.kamanin.nstu.graduate.thesis.shared.exam.domain.entity.TaskType

data class TaskItem(
	val id: Long,
	val number: Int,
	val text: String,
	val theme: String,
	val taskType: TaskType,
	val status: Status
) {

	enum class Status {
		CHECKING,
		REJECTED,
		REVISION,
		APPROVED
	}
}