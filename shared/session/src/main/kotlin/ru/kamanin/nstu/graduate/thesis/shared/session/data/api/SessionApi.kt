package ru.kamanin.nstu.graduate.thesis.shared.session.data.api

import retrofit2.http.Header
import retrofit2.http.POST
import ru.kamanin.nstu.graduate.thesis.component.core.network.Headers
import ru.kamanin.nstu.graduate.thesis.shared.session.data.dto.SessionDto

interface SessionApi {

	@POST("/login")
	suspend fun login(@Header(Headers.XAuthentication) auth: String): SessionDto
}