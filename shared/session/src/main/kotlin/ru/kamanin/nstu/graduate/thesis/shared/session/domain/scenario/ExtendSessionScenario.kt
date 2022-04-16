package ru.kamanin.nstu.graduate.thesis.shared.session.domain.scenario

import ru.kamanin.nstu.graduate.thesis.shared.account.domain.usecase.GetAccountUseCase
import ru.kamanin.nstu.graduate.thesis.shared.session.domain.usecase.LoginUseCase
import javax.inject.Inject

class ExtendSessionScenario @Inject constructor(
	private val loginUseCase: LoginUseCase,
	private val getAccountUseCase: GetAccountUseCase
) {

	suspend operator fun invoke() {
		val account = getAccountUseCase()
		loginUseCase(account.username, account.password)
	}
}