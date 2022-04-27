package ru.kamanin.nstu.graduate.thesis.shared.account.data.api.notauthorized

import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import ru.kamanin.nstu.graduate.thesis.shared.account.data.dto.PasswordDto
import ru.kamanin.nstu.graduate.thesis.shared.api.headers.Headers.X_Authentication

interface AccountApi {

	@POST("/account/change-password")
	suspend fun changePassword(@Header(X_Authentication) auth: String, @Body password: PasswordDto)
}