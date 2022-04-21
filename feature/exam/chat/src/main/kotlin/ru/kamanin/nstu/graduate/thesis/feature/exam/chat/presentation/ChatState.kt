package ru.kamanin.nstu.graduate.thesis.feature.exam.chat.presentation

import androidx.paging.PagingData
import ru.kamanin.nstu.graduate.thesis.feature.exam.chat.presentation.model.MessageItem

sealed interface ChatState {

	object Initial : ChatState

	object Loading : ChatState

	data class Content(val messages: PagingData<MessageItem>) : ChatState
}