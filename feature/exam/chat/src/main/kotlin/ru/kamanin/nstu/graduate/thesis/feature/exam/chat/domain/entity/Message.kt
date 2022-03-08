package ru.kamanin.nstu.graduate.thesis.feature.exam.chat.domain.entity

sealed interface Message {

	val id: Long
	val text: String
	val time: String

	data class SentMessage(
		override val id: Long,
		override val text: String,
		override val time: String
	) : Message

	data class ReceivedMessage(
		override val id: Long,
		override val text: String,
		override val time: String
	) : Message
}