package ru.kamanin.nstu.graduate.thesis.shared.account.data.api.authorized

import retrofit2.http.GET
import retrofit2.http.Path
import ru.kamanin.nstu.graduate.thesis.shared.account.data.dto.AccountDto

interface AccountApi {

	@GET("/account/me")
	suspend fun getPersonal(): AccountDto

	@GET("/account/{accountId}")
	suspend fun getById(@Path("accountId") accountId: Long): AccountDto
}