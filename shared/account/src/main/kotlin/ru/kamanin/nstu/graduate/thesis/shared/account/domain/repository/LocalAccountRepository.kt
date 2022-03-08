package ru.kamanin.nstu.graduate.thesis.shared.account.domain.repository

import ru.kamanin.nstu.graduate.thesis.shared.account.domain.entity.Account

interface LocalAccountRepository {

	suspend fun save(account: Account)

	suspend fun get(): Account

	suspend fun clear()
}