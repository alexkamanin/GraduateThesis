package ru.kamanin.nstu.graduate.thesis.shared.account.data.repository

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import ru.kamanin.nstu.graduate.thesis.shared.account.data.dto.PasswordDto
import ru.kamanin.nstu.graduate.thesis.shared.account.data.mapper.toEntity
import ru.kamanin.nstu.graduate.thesis.shared.account.domain.entity.Account
import ru.kamanin.nstu.graduate.thesis.shared.account.domain.repository.AccountRepository
import ru.kamanin.nstu.graduate.thesis.utils.coroutines.dispatcher.ioDispatcher
import ru.kamanin.nstu.graduate.thesis.utils.encoding.Base64Encoder
import javax.inject.Inject
import ru.kamanin.nstu.graduate.thesis.shared.account.data.api.authorized.AccountApi as AuthorizedAccountApi
import ru.kamanin.nstu.graduate.thesis.shared.account.data.api.notauthorized.AccountApi as NotAuthorizedAccountApi

class AccountRepositoryImpl @Inject constructor(
	@ioDispatcher private val ioDispatcher: CoroutineDispatcher,
	private val notAuthorizedApi: NotAuthorizedAccountApi,
	private val authorizedApi: AuthorizedAccountApi
) : AccountRepository {

	override suspend fun changePassword(username: String, password: String, verificationCode: String) {
		withContext(ioDispatcher) {
			val authValue = Base64Encoder.encode(firstValue = username, secondValue = verificationCode)
			notAuthorizedApi.changePassword(authValue, PasswordDto(password))
		}
	}

	override suspend fun getPersonal(): Account =
		withContext(ioDispatcher) {
			authorizedApi.getPersonal().toEntity()
		}

	override suspend fun getById(accountId: Long): Account =
		withContext(ioDispatcher) {
			authorizedApi.getById(accountId).toEntity()
		}
}