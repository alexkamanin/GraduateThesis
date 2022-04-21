package ru.kamanin.nstu.graduate.thesis.shared.chat.domain.entity

data class Message(
	val id: Long,
	val text: String?,
	val sendTime: String,
	val accountId: Long,
	val artefactId: Long?
)