package ru.kamanin.graduate.thesis.shared.notification.domain.usecase

import ru.kamanin.graduate.thesis.shared.notification.domain.repository.LocalFirebaseNotificationRepository
import ru.kamanin.graduate.thesis.shared.notification.domain.repository.RemoteFirebaseNotificationRepository
import javax.inject.Inject

class SetFirebaseNotificationTokenUseCase @Inject constructor(
	private val remoteRepository: RemoteFirebaseNotificationRepository,
	private val localRepository: LocalFirebaseNotificationRepository
) {

	suspend operator fun invoke() {
		val token = localRepository.getToken()

		if (token != null) {
			remoteRepository.sendToken(token)
		}
	}
}