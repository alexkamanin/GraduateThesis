package ru.kamanin.nstu.graduate.thesis.shared.session.data.repository

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import ru.kamanin.nstu.graduate.thesis.component.core.coroutines.dispatcher.ioDispatcher
import ru.kamanin.nstu.graduate.thesis.component.core.encoder.Base64Encoder
import ru.kamanin.nstu.graduate.thesis.shared.session.data.api.SessionApi
import ru.kamanin.nstu.graduate.thesis.shared.session.data.mapper.toEntity
import ru.kamanin.nstu.graduate.thesis.shared.session.domain.entity.Session
import ru.kamanin.nstu.graduate.thesis.shared.session.domain.repository.RemoteSessionRepository
import javax.inject.Inject

class RemoteSessionRepositoryImpl @Inject constructor(
	@ioDispatcher private val ioDispatcher: CoroutineDispatcher,
	private val api: SessionApi
) : RemoteSessionRepository {

	override suspend fun login(username: String, password: String): Session =
		withContext(ioDispatcher) {
			val authValue = Base64Encoder.encode(firstValue = username, secondValue = password)
			api.login(authValue).toEntity()
		}
}