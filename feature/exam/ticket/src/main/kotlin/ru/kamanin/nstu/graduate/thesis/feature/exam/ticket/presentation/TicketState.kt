package ru.kamanin.nstu.graduate.thesis.feature.exam.ticket.presentation

import ru.kamanin.nstu.graduate.thesis.component.core.error.ErrorState
import ru.kamanin.nstu.graduate.thesis.shared.exam.domain.entity.Answer

sealed interface TicketState {

	object Initial : TicketState

	object Loading : TicketState

	data class Content(val answers: List<Answer>) : TicketState

	data class Error(val errorState: ErrorState) : TicketState
}