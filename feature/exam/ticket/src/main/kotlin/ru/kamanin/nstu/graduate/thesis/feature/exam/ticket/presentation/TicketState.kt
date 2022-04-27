package ru.kamanin.nstu.graduate.thesis.feature.exam.ticket.presentation

import ru.kamanin.nstu.graduate.thesis.shared.ticket.domain.entity.Task
import ru.kamanin.nstu.graduate.thesis.utils.error.ErrorState

sealed interface TicketState {

	object Initial : TicketState

	object Loading : TicketState

	data class Content(val tasks: List<Task>) : TicketState

	data class Error(val errorState: ErrorState) : TicketState
}