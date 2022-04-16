package ru.kamanin.nstu.graduate.thesis.navigation.navigation

import ru.kamanin.nstu.graduate.thesis.component.navigation.NavCommand

interface NavigationProvider {

	val toSign: NavCommand

	val toExamList: NavCommand
}