package ru.kamanin.nstu.graduate.thesis.feature.sign.domain.scenario

import ru.kamanin.nstu.graduate.thesis.shared.account.domain.usecase.SaveAccountMetaDataUseCase
import ru.kamanin.nstu.graduate.thesis.shared.session.domain.usecase.LoginUseCase
import javax.inject.Inject

class LoginScenario @Inject constructor(
	private val loginUseCase: LoginUseCase,
	private val saveAccountMetaDataUseCase: SaveAccountMetaDataUseCase
) {

	suspend operator fun invoke(username: String, password: String) {
		loginUseCase(username, password)
		saveAccountMetaDataUseCase(username, password)
	}
}