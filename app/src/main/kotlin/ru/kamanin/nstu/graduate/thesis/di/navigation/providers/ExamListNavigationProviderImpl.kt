package ru.kamanin.nstu.graduate.thesis.di.navigation.providers

import android.os.Bundle
import ru.kamanin.nstu.graduate.thesis.R
import ru.kamanin.nstu.graduate.thesis.component.navigation.NavCommand
import ru.kamanin.nstu.graduate.thesis.feature.exam.list.navigation.ExamListNavigationProvider
import javax.inject.Inject

class ExamListNavigationProviderImpl @Inject constructor() : ExamListNavigationProvider {

	override fun toSignIn(): NavCommand = NavCommand(R.id.from_exam_list_to_sign_in)

	override fun toTicket(args: Bundle): NavCommand = NavCommand(
		action = R.id.from_exam_list_to_ticket,
		args = args
	)
}