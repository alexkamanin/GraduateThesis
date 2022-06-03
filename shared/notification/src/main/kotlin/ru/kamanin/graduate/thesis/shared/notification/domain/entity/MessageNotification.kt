package ru.kamanin.graduate.thesis.shared.notification.domain.entity

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MessageNotification(
	val id: Long,
	val text: String?,
	val sendTime: Long,
	val artefactId: Long?,
	val accountId: Long
)