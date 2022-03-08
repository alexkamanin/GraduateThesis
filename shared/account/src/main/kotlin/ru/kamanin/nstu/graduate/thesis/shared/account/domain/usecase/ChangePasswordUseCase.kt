package ru.kamanin.nstu.graduate.thesis.shared.account.domain.usecase

import ru.kamanin.nstu.graduate.thesis.shared.account.domain.repository.RemoteAccountRepository
import javax.inject.Inject

class ChangePasswordUseCase @Inject constructor(
	private val repository: RemoteAccountRepository
) {

	suspend operator fun invoke(username: String, password: String, verificationCode: String) {
		repository.changePassword(
			username = username,
			password = password,
			verificationCode = verificationCode
		)
	}
}