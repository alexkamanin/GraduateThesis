package ru.kamanin.nstu.graduate.thesis.di.navigation.providers

import ru.kamanin.nstu.graduate.thesis.R
import ru.kamanin.nstu.graduate.thesis.component.navigation.NavCommand
import ru.kamanin.nstu.graduate.thesis.navigation.navigation.NavigationProvider
import javax.inject.Inject

class NavigationProviderImpl @Inject constructor() : NavigationProvider {

	override val toSign: NavCommand = NavCommand(R.id.from_navigation_to_sign_in)

	override val toExamList: NavCommand = NavCommand(R.id.from_navigation_to_exam_list)
}