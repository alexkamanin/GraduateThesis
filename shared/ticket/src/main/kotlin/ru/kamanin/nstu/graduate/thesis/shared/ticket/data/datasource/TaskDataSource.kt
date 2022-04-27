package ru.kamanin.nstu.graduate.thesis.shared.ticket.data.datasource

import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import ru.kamanin.nstu.graduate.thesis.shared.chat.data.dto.MessageDto
import ru.kamanin.nstu.graduate.thesis.shared.ticket.data.api.TicketApi
import ru.kamanin.nstu.graduate.thesis.utils.paging.Paging
import ru.kamanin.nstu.graduate.thesis.utils.paging.PagingDataSource

class TaskDataSource @AssistedInject constructor(
	private val api: TicketApi,
	@Assisted private val answerId: Long
) : PagingDataSource<MessageDto>() {

	@AssistedFactory
	interface Factory {

		fun create(periodId: Long): TaskDataSource
	}

	override suspend fun load(pageNumber: Int, pageSize: Int): Paging<MessageDto> =
		api.getMessages(
			answerId = answerId,
			pageNumber = pageNumber,
			pageSize = pageSize
		)
}