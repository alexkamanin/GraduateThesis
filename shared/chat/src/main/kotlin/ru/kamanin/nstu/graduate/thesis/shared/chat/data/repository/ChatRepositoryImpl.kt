package ru.kamanin.nstu.graduate.thesis.shared.chat.data.repository

import androidx.paging.Pager
import androidx.paging.PagingData
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import ru.kamanin.nstu.graduate.thesis.shared.chat.data.api.ChatApi
import ru.kamanin.nstu.graduate.thesis.shared.chat.data.datasource.ChatDataSource
import ru.kamanin.nstu.graduate.thesis.shared.chat.data.dto.MessageDto
import ru.kamanin.nstu.graduate.thesis.shared.chat.data.mapper.toEntity
import ru.kamanin.nstu.graduate.thesis.shared.chat.domain.entity.Message
import ru.kamanin.nstu.graduate.thesis.shared.chat.domain.entity.MessageSummary
import ru.kamanin.nstu.graduate.thesis.shared.chat.domain.repository.ChatRepository
import ru.kamanin.nstu.graduate.thesis.utils.coroutines.dispatcher.ioDispatcher
import ru.kamanin.nstu.graduate.thesis.utils.paging.PagingDataSource
import ru.kamanin.nstu.graduate.thesis.utils.paging.mapPaging
import javax.inject.Inject

class ChatRepositoryImpl @Inject constructor(
	@ioDispatcher private val ioDispatcher: CoroutineDispatcher,
	private val chatDataSourceFactory: ChatDataSource.Factory,
	private val api: ChatApi
) : ChatRepository {

	override fun getMessages(periodId: Long): Flow<PagingData<Message>> =
		Pager(PagingDataSource.PAGING_CONFIG) { chatDataSourceFactory.create(periodId) }
			.flow
			.flowOn(ioDispatcher)
			.mapPaging(MessageDto::toEntity)

	override suspend fun sendMessage(periodId: Long, message: MessageSummary): Message =
		withContext(ioDispatcher) {
			api.sendMessage(periodId, message).toEntity()
		}
}