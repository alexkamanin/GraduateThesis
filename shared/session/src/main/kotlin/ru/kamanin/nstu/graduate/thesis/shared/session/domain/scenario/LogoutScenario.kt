package ru.kamanin.nstu.graduate.thesis.shared.session.domain.scenario

import ru.kamanin.nstu.graduate.thesis.shared.account.domain.usecase.ClearAccountMetaDataUseCase
import ru.kamanin.nstu.graduate.thesis.shared.session.domain.usecase.LogoutUseCase
import javax.inject.Inject

class LogoutScenario @Inject constructor(
	private val logoutUseCase: LogoutUseCase,
	private val clearAccountMetaDataUseCase: ClearAccountMetaDataUseCase
) {

	suspend operator fun invoke() {
		logoutUseCase()
		clearAccountMetaDataUseCase()
	}
}