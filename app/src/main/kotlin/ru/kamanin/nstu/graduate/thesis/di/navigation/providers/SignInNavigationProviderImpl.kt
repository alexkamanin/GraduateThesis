package ru.kamanin.nstu.graduate.thesis.di.navigation.providers

import ru.kamanin.nstu.graduate.thesis.R
import ru.kamanin.nstu.graduate.thesis.component.navigation.NavCommand
import ru.kamanin.nstu.graduate.thesis.feature.sign.navigation.SignInNavigationProvider
import javax.inject.Inject

class SignInNavigationProviderImpl @Inject constructor() : SignInNavigationProvider {

	override val toSignUp: NavCommand = NavCommand(R.id.action_singInFragment_to_singUpFragment)
	override val toExamList: NavCommand = NavCommand(R.id.action_singInFragment_to_examListFragment)
}