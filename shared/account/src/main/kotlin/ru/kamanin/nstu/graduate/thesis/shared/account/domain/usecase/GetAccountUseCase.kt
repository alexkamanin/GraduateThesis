package ru.kamanin.nstu.graduate.thesis.shared.account.domain.usecase

import ru.kamanin.nstu.graduate.thesis.shared.account.domain.entity.Account
import ru.kamanin.nstu.graduate.thesis.shared.account.domain.repository.LocalAccountRepository
import javax.inject.Inject

class GetAccountUseCase @Inject constructor(
	private val repository: LocalAccountRepository
) {

	suspend operator fun invoke(): Account =
		repository.get()
}