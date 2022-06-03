package ru.kamanin.graduate.thesis.shared.notification.data.repository

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import ru.kamanin.graduate.thesis.shared.notification.domain.repository.LocalFirebaseNotificationRepository
import javax.inject.Inject

class LocalFirebaseNotificationRepositoryImpl @Inject constructor(
	@ApplicationContext private val context: Context
) : LocalFirebaseNotificationRepository {

	private companion object {

		const val FIREBASE_NOTIFICATION_PREFERENCES = "firebaseNotificationPreferences"
		const val TOKEN_VALUE = "tokenValue"
	}

	private val sessionPreferences by lazy {
		context.getSharedPreferences(FIREBASE_NOTIFICATION_PREFERENCES, Context.MODE_PRIVATE)
	}

	override fun setToken(token: String) {
		sessionPreferences
			.edit()
			.putString(TOKEN_VALUE, token)
			.apply()
	}

	override fun getToken(): String? =
		sessionPreferences.getString(TOKEN_VALUE, null)
}