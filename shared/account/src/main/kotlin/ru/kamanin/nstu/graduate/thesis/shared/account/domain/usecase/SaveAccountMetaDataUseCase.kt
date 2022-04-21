package ru.kamanin.nstu.graduate.thesis.shared.account.domain.usecase

import ru.kamanin.nstu.graduate.thesis.shared.account.domain.entity.AccountMetaData
import ru.kamanin.nstu.graduate.thesis.shared.account.domain.repository.AccountMetaDataRepository
import javax.inject.Inject

class SaveAccountMetaDataUseCase @Inject constructor(
	private val repository: AccountMetaDataRepository
) {

	suspend operator fun invoke(username: String, password: String) {
		repository.set(AccountMetaData(username, password))
	}
}