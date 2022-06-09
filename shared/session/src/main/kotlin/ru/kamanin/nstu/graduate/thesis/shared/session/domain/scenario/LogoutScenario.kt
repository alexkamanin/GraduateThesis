package ru.kamanin.nstu.graduate.thesis.shared.session.domain.scenario

import ru.kamanin.nstu.graduate.thesis.shared.account.domain.usecase.ClearAccountMetaDataUseCase
import ru.kamanin.nstu.graduate.thesis.shared.session.domain.usecase.ChangeAutoExtendSessionUseCase
import ru.kamanin.nstu.graduate.thesis.shared.session.domain.usecase.LogoutUseCase
import javax.inject.Inject

class LogoutScenario @Inject constructor(
	private val logoutUseCase: LogoutUseCase,
	private val clearAccountMetaDataUseCase: ClearAccountMetaDataUseCase,
	private val disableAutoExtendSessionUseCase: ChangeAutoExtendSessionUseCase
) {

	suspend operator fun invoke() {
		logoutUseCase()
		disableAutoExtendSessionUseCase(auto = false) // TASK отображение предыдущих данных для авторизации
//		clearAccountMetaDataUseCase() // TASK отображение предыдущих данных для авторизации
	}
}