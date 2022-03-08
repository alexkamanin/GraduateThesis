package ru.kamanin.nstu.graduate.thesis.feature.sign.domain.scenario

import ru.kamanin.nstu.graduate.thesis.shared.account.domain.usecase.SaveAccountUseCase
import ru.kamanin.nstu.graduate.thesis.shared.session.domain.usecase.LoginUseCase
import javax.inject.Inject

class LoginScenario @Inject constructor(
	private val loginUseCase: LoginUseCase,
	private val saveAccountUseCase: SaveAccountUseCase
) {

	suspend operator fun invoke(username: String, password: String) {
		loginUseCase(username, password)
		saveAccountUseCase(username, password)
	}
}