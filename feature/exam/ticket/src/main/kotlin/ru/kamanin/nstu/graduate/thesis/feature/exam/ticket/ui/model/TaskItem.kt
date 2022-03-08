package ru.kamanin.nstu.graduate.thesis.feature.exam.ticket.ui.model

data class TaskItem(
	val id: Long,
	val name: String,
	val status: Status
) {

	enum class Status {
		CHECKING,
		REJECTED,
		REVISION,
		APPROVED
	}
}