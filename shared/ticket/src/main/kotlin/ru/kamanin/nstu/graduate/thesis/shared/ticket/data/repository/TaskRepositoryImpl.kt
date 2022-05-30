package ru.kamanin.nstu.graduate.thesis.shared.ticket.data.repository

import androidx.paging.Pager
import androidx.paging.PagingData
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import ru.kamanin.nstu.graduate.thesis.shared.chat.data.dto.MessageDto
import ru.kamanin.nstu.graduate.thesis.shared.chat.data.mapper.toEntity
import ru.kamanin.nstu.graduate.thesis.shared.chat.domain.entity.Message
import ru.kamanin.nstu.graduate.thesis.shared.chat.domain.entity.MessageSummary
import ru.kamanin.nstu.graduate.thesis.shared.ticket.data.api.TicketApi
import ru.kamanin.nstu.graduate.thesis.shared.ticket.data.datasource.TaskDataSource
import ru.kamanin.nstu.graduate.thesis.shared.ticket.data.dto.TaskStateModel
import ru.kamanin.nstu.graduate.thesis.shared.ticket.domain.entity.TaskState
import ru.kamanin.nstu.graduate.thesis.shared.ticket.domain.repository.TaskRepository
import ru.kamanin.nstu.graduate.thesis.utils.coroutines.dispatcher.ioDispatcher
import ru.kamanin.nstu.graduate.thesis.utils.paging.PagingDataSource
import ru.kamanin.nstu.graduate.thesis.utils.paging.mapPaging
import javax.inject.Inject

class TaskRepositoryImpl @Inject constructor(
	@ioDispatcher private val ioDispatcher: CoroutineDispatcher,
	private val taskDataSourceFactory: TaskDataSource.Factory,
	private val api: TicketApi
) : TaskRepository {

	override fun getMessages(answerId: Long): Flow<PagingData<Message>> =
		Pager(PagingDataSource.PAGING_CONFIG) { taskDataSourceFactory.create(answerId) }
			.flow
			.flowOn(ioDispatcher)
			.mapPaging(MessageDto::toEntity)

	override suspend fun sendMessage(answerId: Long, message: MessageSummary): Message =
		withContext(ioDispatcher) {
			api.sendMessage(answerId, message).toEntity()
		}

	override suspend fun setState(id: Long, state: TaskState) {
		withContext(ioDispatcher) {
			api.setState(TaskStateModel(id, state))
		}
	}
}