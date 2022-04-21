package ru.kamanin.nstu.graduate.thesis.shared.session.domain.usecase

import ru.kamanin.nstu.graduate.thesis.shared.session.domain.repository.LocalSessionRepository
import ru.kamanin.nstu.graduate.thesis.shared.session.domain.repository.RemoteSessionRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
	private val remoteRepository: RemoteSessionRepository,
	private val localRepository: LocalSessionRepository
) {

	suspend operator fun invoke(username: String, password: String) {
		val session = remoteRepository.login(username, password)
		localRepository.set(session)
	}
}