package ru.kamanin.nstu.graduate.thesis.di.navigation.providers

import ru.kamanin.nstu.graduate.thesis.R
import ru.kamanin.nstu.graduate.thesis.component.navigation.NavCommand
import ru.kamanin.nstu.graduate.thesis.feature.exam.ticket.navigation.TicketNavigationProvider
import javax.inject.Inject

class TicketNavigationProviderImpl @Inject constructor() : TicketNavigationProvider {

	override val toChat: NavCommand = NavCommand(R.id.action_ticketFragment_to_chatFragment)

	override val toTask: NavCommand = NavCommand(R.id.action_ticketFragment_to_taskFragment)
}