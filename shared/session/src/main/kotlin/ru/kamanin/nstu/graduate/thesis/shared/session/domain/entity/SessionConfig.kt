package ru.kamanin.nstu.graduate.thesis.shared.session.domain.entity

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SessionConfig(
	val autoExtend: Boolean = true
)