package ru.kamanin.nstu.graduate.thesis.feature.exam.list.domain.scenario

import ru.kamanin.nstu.graduate.thesis.shared.account.domain.usecase.ClearAccountUseCase
import ru.kamanin.nstu.graduate.thesis.shared.session.domain.usecase.LogoutUseCase
import javax.inject.Inject

class LogoutScenario @Inject constructor(
	private val logoutUseCase: LogoutUseCase,
	private val clearAccountUseCase: ClearAccountUseCase
) {

	suspend operator fun invoke() {
		logoutUseCase()
		clearAccountUseCase()
	}
}