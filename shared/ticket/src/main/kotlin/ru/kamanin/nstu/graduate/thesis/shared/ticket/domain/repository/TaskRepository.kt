package ru.kamanin.nstu.graduate.thesis.shared.ticket.domain.repository

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import ru.kamanin.nstu.graduate.thesis.shared.chat.domain.entity.Message
import ru.kamanin.nstu.graduate.thesis.shared.chat.domain.entity.MessageSummary

interface TaskRepository {

	fun getMessages(answerId: Long): Flow<PagingData<Message>>

	suspend fun sendMessage(answerId: Long, message: MessageSummary): Message
}