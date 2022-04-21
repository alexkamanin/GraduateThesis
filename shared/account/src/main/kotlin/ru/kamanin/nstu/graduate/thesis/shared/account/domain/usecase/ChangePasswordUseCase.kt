package ru.kamanin.nstu.graduate.thesis.shared.account.domain.usecase

import ru.kamanin.nstu.graduate.thesis.shared.account.domain.repository.AccountRepository
import javax.inject.Inject

class ChangePasswordUseCase @Inject constructor(
	private val repository: AccountRepository
) {

	suspend operator fun invoke(username: String, password: String, verificationCode: String) {
		repository.changePassword(
			username = username,
			password = password,
			verificationCode = verificationCode
		)
	}
}