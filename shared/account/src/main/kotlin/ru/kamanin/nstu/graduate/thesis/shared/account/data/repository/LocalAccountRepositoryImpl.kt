package ru.kamanin.nstu.graduate.thesis.shared.account.data.repository

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import ru.kamanin.nstu.graduate.thesis.component.core.coroutines.dispatcher.ioDispatcher
import ru.kamanin.nstu.graduate.thesis.shared.account.domain.entity.Account
import ru.kamanin.nstu.graduate.thesis.shared.account.domain.repository.LocalAccountRepository
import javax.inject.Inject

class LocalAccountRepositoryImpl @Inject constructor(
	@ioDispatcher private val ioDispatcher: CoroutineDispatcher,
	@ApplicationContext private val context: Context
) : LocalAccountRepository {

	private companion object {

		const val ACCOUNT_PREFERENCES = "accountPreferences"
		const val ACCOUNT_USERNAME = "accountUsername"
		const val ACCOUNT_PASSWORD = "accountPassword"
	}

	private val accountPreferences by lazy {
		context.getSharedPreferences(ACCOUNT_PREFERENCES, Context.MODE_PRIVATE)
	}

	override suspend fun save(account: Account) {
		withContext(ioDispatcher) {
			accountPreferences
				.edit()
				.putString(ACCOUNT_USERNAME, account.username)
				.putString(ACCOUNT_PASSWORD, account.password)
				.apply()
		}
	}

	override suspend fun get(): Account =
		withContext(ioDispatcher) {
			val username = accountPreferences.getString(ACCOUNT_USERNAME, null) ?: throw IllegalStateException("Account username not found")
			val password = accountPreferences.getString(ACCOUNT_PASSWORD, null) ?: throw IllegalStateException("Account password not found")

			Account(username, password)
		}

	override suspend fun clear() {
		withContext(ioDispatcher) {
			accountPreferences
				.edit()
				.clear()
				.apply()
		}
	}
}