package ru.kamanin.graduate.thesis.shared.notification.data.repository

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import ru.kamanin.graduate.thesis.shared.notification.data.api.FirebaseApi
import ru.kamanin.graduate.thesis.shared.notification.data.dto.FirebaseToken
import ru.kamanin.graduate.thesis.shared.notification.domain.repository.RemoteFirebaseNotificationRepository
import ru.kamanin.nstu.graduate.thesis.utils.coroutines.dispatcher.ioDispatcher
import javax.inject.Inject

class RemoteFirebaseNotificationRepositoryImpl @Inject constructor(
	@ioDispatcher private val ioDispatcher: CoroutineDispatcher,
	private val api: FirebaseApi
) : RemoteFirebaseNotificationRepository {

	override suspend fun sendToken(token: String) {
		withContext(ioDispatcher) {
			api.sendToken(FirebaseToken(token = token))
		}
	}
}