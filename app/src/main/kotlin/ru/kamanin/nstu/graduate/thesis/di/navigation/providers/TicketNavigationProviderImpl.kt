package ru.kamanin.nstu.graduate.thesis.di.navigation.providers

import android.os.Bundle
import ru.kamanin.nstu.graduate.thesis.R
import ru.kamanin.nstu.graduate.thesis.component.navigation.NavCommand
import ru.kamanin.nstu.graduate.thesis.feature.exam.ticket.navigation.TicketNavigationProvider
import javax.inject.Inject

class TicketNavigationProviderImpl @Inject constructor() : TicketNavigationProvider {

	override fun toChat(args: Bundle): NavCommand = NavCommand(
		action = R.id.from_ticket_to_chat,
		args = args
	)

	override fun toTask(args: Bundle): NavCommand = NavCommand(
		action = R.id.from_ticket_to_task,
		args = args
	)

	override fun toSign(): NavCommand = NavCommand(R.id.from_ticket_to_sign_in)
}