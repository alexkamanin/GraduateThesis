package ru.kamanin.nstu.graduate.thesis.shared.account.domain.usecase

import ru.kamanin.nstu.graduate.thesis.shared.account.domain.entity.Account
import ru.kamanin.nstu.graduate.thesis.shared.account.domain.repository.AccountRepository
import javax.inject.Inject

class GetAccountById @Inject constructor(
	private val repository: AccountRepository
) {

	suspend operator fun invoke(accountId: Long): Account =
		repository.getById(accountId)
}