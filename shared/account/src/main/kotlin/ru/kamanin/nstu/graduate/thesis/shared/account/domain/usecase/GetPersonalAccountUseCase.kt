package ru.kamanin.nstu.graduate.thesis.shared.account.domain.usecase

import ru.kamanin.nstu.graduate.thesis.shared.account.domain.entity.Account
import ru.kamanin.nstu.graduate.thesis.shared.account.domain.repository.AccountRepository
import javax.inject.Inject

class GetPersonalAccountUseCase @Inject constructor(
	private val repository: AccountRepository
) {

	suspend operator fun invoke(): Account =
		repository.getPersonal()
}