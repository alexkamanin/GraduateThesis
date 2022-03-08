package ru.kamanin.nstu.graduate.thesis.shared.session.domain.usecase

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import ru.kamanin.nstu.graduate.thesis.component.core.coroutines.dispatcher.ioDispatcher
import ru.kamanin.nstu.graduate.thesis.shared.session.domain.entity.Session
import ru.kamanin.nstu.graduate.thesis.shared.session.domain.repository.LocalSessionRepository
import javax.inject.Inject

class GetSessionUseCase @Inject constructor(
	@ioDispatcher private val ioDispatcher: CoroutineDispatcher,
	private val repository: LocalSessionRepository
) {

	suspend operator fun invoke(): Session =
		withContext(ioDispatcher) {
			repository.get()
		}
}