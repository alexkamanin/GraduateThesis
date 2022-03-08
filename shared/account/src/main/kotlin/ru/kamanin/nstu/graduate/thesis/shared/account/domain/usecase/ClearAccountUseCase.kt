package ru.kamanin.nstu.graduate.thesis.shared.account.domain.usecase

import ru.kamanin.nstu.graduate.thesis.shared.account.domain.repository.LocalAccountRepository
import javax.inject.Inject

class ClearAccountUseCase @Inject constructor(
	private val repository: LocalAccountRepository
) {

	suspend operator fun invoke() {
		repository.clear()
	}
}