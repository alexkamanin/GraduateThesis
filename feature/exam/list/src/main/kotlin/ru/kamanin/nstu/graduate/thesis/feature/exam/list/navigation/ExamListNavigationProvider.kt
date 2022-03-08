package ru.kamanin.nstu.graduate.thesis.feature.exam.list.navigation

import android.os.Bundle
import ru.kamanin.nstu.graduate.thesis.component.navigation.NavCommand

interface ExamListNavigationProvider {

	fun toSignIn(): NavCommand

	fun toTicket(args: Bundle): NavCommand
}