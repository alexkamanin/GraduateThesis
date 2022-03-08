package ru.kamanin.nstu.graduate.thesis.shared.account.data.repository

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import ru.kamanin.nstu.graduate.thesis.component.core.coroutines.dispatcher.ioDispatcher
import ru.kamanin.nstu.graduate.thesis.component.core.encoder.Base64Encoder
import ru.kamanin.nstu.graduate.thesis.shared.account.data.api.AccountApi
import ru.kamanin.nstu.graduate.thesis.shared.account.data.dto.PasswordDto
import ru.kamanin.nstu.graduate.thesis.shared.account.domain.repository.AccountRepository
import javax.inject.Inject

class AccountRepositoryImpl @Inject constructor(
	@ioDispatcher private val ioDispatcher: CoroutineDispatcher,
	private val api: AccountApi
) : AccountRepository {

	override suspend fun changePassword(username: String, password: String, verificationCode: String) {
		withContext(ioDispatcher) {
			val authValue = Base64Encoder.encode(firstValue = username, secondValue = verificationCode)
			api.changePassword(authValue, PasswordDto(password))
		}
	}
}