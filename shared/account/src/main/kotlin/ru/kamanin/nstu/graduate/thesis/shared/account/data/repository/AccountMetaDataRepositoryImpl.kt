package ru.kamanin.nstu.graduate.thesis.shared.account.data.repository

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import ru.kamanin.nstu.graduate.thesis.component.core.coroutines.dispatcher.ioDispatcher
import ru.kamanin.nstu.graduate.thesis.shared.account.domain.entity.AccountMetaData
import ru.kamanin.nstu.graduate.thesis.shared.account.domain.repository.AccountMetaDataRepository
import javax.inject.Inject

class AccountMetaDataRepositoryImpl @Inject constructor(
	@ioDispatcher private val ioDispatcher: CoroutineDispatcher,
	@ApplicationContext private val context: Context
) : AccountMetaDataRepository {

	private companion object {

		const val ACCOUNT_METADATA_PREFERENCES = "accountMetaDataPreferences"
		const val ACCOUNT_USERNAME = "accountUsername"
		const val ACCOUNT_PASSWORD = "accountPassword"
	}

	private val accountMetaDataPreferences by lazy {
		context.getSharedPreferences(ACCOUNT_METADATA_PREFERENCES, Context.MODE_PRIVATE)
	}

	override suspend fun set(accountMetaData: AccountMetaData) {
		withContext(ioDispatcher) {
			accountMetaDataPreferences
				.edit()
				.putString(ACCOUNT_USERNAME, accountMetaData.username)
				.putString(ACCOUNT_PASSWORD, accountMetaData.password)
				.apply()
		}
	}

	override suspend fun get(): AccountMetaData =
		withContext(ioDispatcher) {
			val username = accountMetaDataPreferences.getString(ACCOUNT_USERNAME, null) ?: throw IllegalStateException("Account username not found")
			val password = accountMetaDataPreferences.getString(ACCOUNT_PASSWORD, null) ?: throw IllegalStateException("Account password not found")

			AccountMetaData(username, password)
		}

	override suspend fun clear() {
		withContext(ioDispatcher) {
			accountMetaDataPreferences
				.edit()
				.clear()
				.apply()
		}
	}
}