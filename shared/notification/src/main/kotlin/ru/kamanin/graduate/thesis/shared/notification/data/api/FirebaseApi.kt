package ru.kamanin.graduate.thesis.shared.notification.data.api

import retrofit2.http.Body
import retrofit2.http.POST
import ru.kamanin.graduate.thesis.shared.notification.data.dto.FirebaseToken

interface FirebaseApi {

	@POST("/account/firebase-token")
	suspend fun sendToken(@Body token: FirebaseToken)
}