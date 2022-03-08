package ru.kamanin.nstu.graduate.thesis.shared.session.domain.usecase

import ru.kamanin.nstu.graduate.thesis.shared.session.domain.repository.LocalSessionRepository
import javax.inject.Inject

class LogoutUseCase @Inject constructor(
	private val repository: LocalSessionRepository
) {

	suspend operator fun invoke() {
		repository.clear()
	}
}