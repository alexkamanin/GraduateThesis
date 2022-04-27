package ru.kamanin.nstu.graduate.thesis.shared.session.data.repository

import android.content.Context
import com.squareup.moshi.Moshi
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import ru.kamanin.nstu.graduate.thesis.shared.session.domain.entity.Session
import ru.kamanin.nstu.graduate.thesis.shared.session.domain.repository.LocalSessionRepository
import ru.kamanin.nstu.graduate.thesis.utils.coroutines.dispatcher.ioDispatcher
import javax.inject.Inject

class LocalSessionRepositoryImpl @Inject constructor(
	@ioDispatcher private val ioDispatcher: CoroutineDispatcher,
	@ApplicationContext private val context: Context
) : LocalSessionRepository {

	private companion object {

		const val SESSION_PREFERENCES = "sessionPreferences"
		const val SESSION_VALUE = "sessionValue"
	}

	private val sessionPreferences by lazy {
		context.getSharedPreferences(SESSION_PREFERENCES, Context.MODE_PRIVATE)
	}

	private val sessionAdapter by lazy {
		val moshi = Moshi.Builder().build()
		moshi.adapter(Session::class.java)
	}

	override suspend fun set(session: Session) {
		withContext(ioDispatcher) {
			sessionPreferences
				.edit()
				.putString(SESSION_VALUE, sessionAdapter.toJson(session))
				.apply()
		}
	}

	override fun get(): Session =
		sessionPreferences.getString(SESSION_VALUE, null)
			?.let(sessionAdapter::fromJson)
			?: throw IllegalStateException("Session is not found")

	override suspend fun clear() {
		withContext(ioDispatcher) {
			sessionPreferences
				.edit()
				.clear()
				.apply()
		}
	}
}