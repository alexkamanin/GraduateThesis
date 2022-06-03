package ru.kamanin.graduate.thesis.shared.notification.domain.usecase

import ru.kamanin.graduate.thesis.shared.notification.domain.repository.LocalFirebaseNotificationRepository
import javax.inject.Inject

class UpdateFirebaseNotificationTokenUseCase @Inject constructor(
	private val repository: LocalFirebaseNotificationRepository
) {

	operator fun invoke(token: String) {
		repository.setToken(token)
	}
}