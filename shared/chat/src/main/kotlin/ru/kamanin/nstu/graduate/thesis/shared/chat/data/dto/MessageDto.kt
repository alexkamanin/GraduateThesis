package ru.kamanin.nstu.graduate.thesis.shared.chat.data.dto

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MessageDto(
	val id: Long,
	val text: String?,
	val sendTime: Long,
	val accountId: Long,
	val artefactId: Long?
)