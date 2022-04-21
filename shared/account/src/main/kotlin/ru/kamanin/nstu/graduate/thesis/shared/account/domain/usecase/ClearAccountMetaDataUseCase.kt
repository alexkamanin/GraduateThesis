package ru.kamanin.nstu.graduate.thesis.shared.account.domain.usecase

import ru.kamanin.nstu.graduate.thesis.shared.account.domain.repository.AccountMetaDataRepository
import javax.inject.Inject

class ClearAccountMetaDataUseCase @Inject constructor(
	private val repository: AccountMetaDataRepository
) {

	suspend operator fun invoke() {
		repository.clear()
	}
}