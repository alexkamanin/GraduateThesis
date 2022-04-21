package ru.kamanin.nstu.graduate.thesis.shared.account.domain.repository

import ru.kamanin.nstu.graduate.thesis.shared.account.domain.entity.Account

interface AccountRepository {

	suspend fun changePassword(username: String, password: String, verificationCode: String)

	suspend fun getPersonal(): Account

	suspend fun getById(accountId: Long): Account
}