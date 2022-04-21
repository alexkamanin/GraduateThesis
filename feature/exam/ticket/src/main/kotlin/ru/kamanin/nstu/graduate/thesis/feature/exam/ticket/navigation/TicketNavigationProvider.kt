package ru.kamanin.nstu.graduate.thesis.feature.exam.ticket.navigation

import android.os.Bundle
import ru.kamanin.nstu.graduate.thesis.component.navigation.NavCommand

interface TicketNavigationProvider {

	fun toChat(args: Bundle): NavCommand

	fun toTask(args: Bundle): NavCommand

	fun toSign(): NavCommand
}