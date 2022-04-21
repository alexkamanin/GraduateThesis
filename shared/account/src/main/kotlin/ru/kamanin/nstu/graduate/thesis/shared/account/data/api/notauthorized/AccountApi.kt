package ru.kamanin.nstu.graduate.thesis.shared.account.data.api.notauthorized

import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import ru.kamanin.nstu.graduate.thesis.component.core.network.Headers.X_Authentication
import ru.kamanin.nstu.graduate.thesis.shared.account.data.dto.PasswordDto

interface AccountApi {

	@POST("/account/change-password")
	suspend fun changePassword(@Header(X_Authentication) auth: String, @Body password: PasswordDto)
}