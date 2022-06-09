package ru.kamanin.nstu.graduate.thesis.shared.session.data.repository

import android.content.Context
import com.squareup.moshi.Moshi
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import ru.kamanin.nstu.graduate.thesis.shared.session.domain.entity.SessionConfig
import ru.kamanin.nstu.graduate.thesis.utils.coroutines.dispatcher.ioDispatcher
import javax.inject.Inject

interface SessionConfigRepository {

	suspend fun set(config: SessionConfig)

	suspend fun get(): SessionConfig
}

class SessionConfigRepositoryImpl @Inject constructor(
	@ioDispatcher private val ioDispatcher: CoroutineDispatcher,
	@ApplicationContext private val context: Context
) : SessionConfigRepository {

	private companion object {

		const val SESSION_PREFERENCES = "sessionConfigPreferences"
		const val SESSION_CONFIG_VALUE = "sessionConfigValue"
	}

	private val sessionPreferences by lazy {
		context.getSharedPreferences(SESSION_PREFERENCES, Context.MODE_PRIVATE)
	}

	private val configAdapter by lazy {
		val moshi = Moshi.Builder().build()
		moshi.adapter(SessionConfig::class.java)
	}

	override suspend fun set(config: SessionConfig) {
		withContext(ioDispatcher) {
			sessionPreferences
				.edit()
				.putString(SESSION_CONFIG_VALUE, configAdapter.toJson(config))
				.apply()
		}
	}

	override suspend fun get(): SessionConfig =
		sessionPreferences.getString(SESSION_CONFIG_VALUE, null)
			?.let(configAdapter::fromJson)
			?: SessionConfig()
}