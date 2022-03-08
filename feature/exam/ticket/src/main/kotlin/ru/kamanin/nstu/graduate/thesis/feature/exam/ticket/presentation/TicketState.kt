package ru.kamanin.nstu.graduate.thesis.feature.exam.ticket.presentation

import ru.kamanin.nstu.graduate.thesis.feature.exam.ticket.ui.model.TaskItem

sealed interface TicketState {

	object Loading : TicketState

	data class Content(val taskItems: List<TaskItem>) : TicketState
}