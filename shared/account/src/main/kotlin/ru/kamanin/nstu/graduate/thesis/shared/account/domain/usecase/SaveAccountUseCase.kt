package ru.kamanin.nstu.graduate.thesis.shared.account.domain.usecase

import ru.kamanin.nstu.graduate.thesis.shared.account.domain.entity.Account
import ru.kamanin.nstu.graduate.thesis.shared.account.domain.repository.LocalAccountRepository
import javax.inject.Inject

class SaveAccountUseCase @Inject constructor(
	private val repository: LocalAccountRepository
) {

	suspend operator fun invoke(username: String, password: String) {
		repository.save(Account(username, password))
	}
}