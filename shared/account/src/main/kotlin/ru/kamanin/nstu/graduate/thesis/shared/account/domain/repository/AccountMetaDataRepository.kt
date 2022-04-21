package ru.kamanin.nstu.graduate.thesis.shared.account.domain.repository

import ru.kamanin.nstu.graduate.thesis.shared.account.domain.entity.AccountMetaData

interface AccountMetaDataRepository {

	suspend fun set(accountMetaData: AccountMetaData)

	suspend fun get(): AccountMetaData

	suspend fun clear()
}