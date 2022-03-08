package ru.kamanin.nstu.graduate.thesis.feature.exam.chat.presentation

import ru.kamanin.nstu.graduate.thesis.feature.exam.chat.domain.entity.Message

sealed interface ChatState {

	object Initial : ChatState

	data class Content(
		val messageItems: List<Message>,
		val needClearMessageText: Boolean
	) : ChatState
}