package ru.kamanin.nstu.graduate.thesis.di.navigation.providers

import ru.kamanin.nstu.graduate.thesis.R
import ru.kamanin.nstu.graduate.thesis.component.navigation.NavCommand
import ru.kamanin.nstu.graduate.thesis.feature.exam.list.navigation.ExamListNavigationProvider
import javax.inject.Inject

class ExamListNavigationProviderImpl @Inject constructor() : ExamListNavigationProvider {

	override val toSignIn: NavCommand = NavCommand(R.id.action_examListFragment_to_singInFragment)
	override val toTicket: NavCommand = NavCommand(R.id.action_examListFragment_to_ticketFragment)
}