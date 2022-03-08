package ru.kamanin.nstu.graduate.thesis.shared.session.data.dto

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SessionDto(
	val token: String,
	val roles: String
)