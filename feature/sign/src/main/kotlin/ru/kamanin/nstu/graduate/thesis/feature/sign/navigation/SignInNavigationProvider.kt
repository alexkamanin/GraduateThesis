package ru.kamanin.nstu.graduate.thesis.feature.sign.navigation

import ru.kamanin.nstu.graduate.thesis.component.navigation.NavCommand

interface SignInNavigationProvider {

	val toSignUp: NavCommand

	val toExamList: NavCommand
}