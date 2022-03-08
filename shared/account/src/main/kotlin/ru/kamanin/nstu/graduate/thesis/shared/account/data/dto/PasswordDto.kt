package ru.kamanin.nstu.graduate.thesis.shared.account.data.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PasswordDto(
	@field:Json(name = "password")
	val value: String
)