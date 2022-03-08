package ru.kamanin.nstu.graduate.thesis.feature.exam.list.navigation

import ru.kamanin.nstu.graduate.thesis.component.navigation.NavCommand

interface ExamListNavigationProvider {

	val toSignIn: NavCommand
	val toTicket: NavCommand
}