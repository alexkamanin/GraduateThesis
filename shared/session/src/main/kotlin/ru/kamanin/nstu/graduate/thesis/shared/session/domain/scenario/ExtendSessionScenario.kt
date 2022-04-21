package ru.kamanin.nstu.graduate.thesis.shared.session.domain.scenario

import ru.kamanin.nstu.graduate.thesis.shared.account.domain.usecase.GetAccountMetaDataUseCase
import ru.kamanin.nstu.graduate.thesis.shared.session.domain.usecase.LoginUseCase
import javax.inject.Inject

class ExtendSessionScenario @Inject constructor(
	private val loginUseCase: LoginUseCase,
	private val getAccountMetaDataUseCase: GetAccountMetaDataUseCase
) {

	suspend operator fun invoke() {
		val metaData = getAccountMetaDataUseCase()
		loginUseCase(metaData.username, metaData.password)
	}
}