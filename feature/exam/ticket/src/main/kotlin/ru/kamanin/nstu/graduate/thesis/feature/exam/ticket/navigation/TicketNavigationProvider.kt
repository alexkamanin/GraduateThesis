package ru.kamanin.nstu.graduate.thesis.feature.exam.ticket.navigation

import ru.kamanin.nstu.graduate.thesis.component.navigation.NavCommand

interface TicketNavigationProvider {

	val toChat: NavCommand
	val toTask: NavCommand
}