package ru.kamanin.nstu.graduate.thesis.shared.session.data.api

import retrofit2.http.Header
import retrofit2.http.POST
import ru.kamanin.nstu.graduate.thesis.component.core.network.Headers.X_Authentication
import ru.kamanin.nstu.graduate.thesis.shared.session.data.dto.SessionDto

interface SessionApi {

	@POST("/login")
	suspend fun login(@Header(X_Authentication) auth: String): SessionDto
}