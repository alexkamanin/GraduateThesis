package ru.kamanin.nstu.graduate.thesis.di.navigation.providers

import ru.kamanin.nstu.graduate.thesis.R
import ru.kamanin.nstu.graduate.thesis.component.navigation.NavCommand
import ru.kamanin.nstu.graduate.thesis.feature.sign.navigation.SignUpNavigationProvider
import javax.inject.Inject

class SignUpNavigationProviderImpl @Inject constructor() : SignUpNavigationProvider {

	override val toExamList: NavCommand = NavCommand(R.id.from_sign_up_to_exam_list)
}