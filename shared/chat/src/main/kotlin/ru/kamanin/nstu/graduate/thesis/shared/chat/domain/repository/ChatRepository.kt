package ru.kamanin.nstu.graduate.thesis.shared.chat.domain.repository

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import ru.kamanin.nstu.graduate.thesis.shared.chat.domain.entity.Message
import ru.kamanin.nstu.graduate.thesis.shared.chat.domain.entity.MessageSummary

interface ChatRepository {

	fun getMessages(periodId: Long): Flow<PagingData<Message>>

	suspend fun sendMessage(periodId: Long, message: MessageSummary): Message
}