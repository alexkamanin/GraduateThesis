package ru.kamanin.nstu.graduate.thesis.shared.session.domain.repository

import ru.kamanin.nstu.graduate.thesis.shared.session.domain.entity.Session

interface RemoteSessionRepository {

	suspend fun login(username: String, password: String): Session
}