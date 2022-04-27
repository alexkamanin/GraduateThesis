package ru.kamanin.nstu.graduate.thesis.shared.chat.data.datasource

import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import ru.kamanin.nstu.graduate.thesis.shared.chat.data.api.ChatApi
import ru.kamanin.nstu.graduate.thesis.shared.chat.data.dto.MessageDto
import ru.kamanin.nstu.graduate.thesis.utils.paging.Paging
import ru.kamanin.nstu.graduate.thesis.utils.paging.PagingDataSource

class ChatDataSource @AssistedInject constructor(
	private val api: ChatApi,
	@Assisted private val periodId: Long
) : PagingDataSource<MessageDto>() {

	@AssistedFactory
	interface Factory {

		fun create(periodId: Long): ChatDataSource
	}

	override suspend fun load(pageNumber: Int, pageSize: Int): Paging<MessageDto> =
		api.getMessages(
			periodId = periodId,
			pageNumber = pageNumber,
			pageSize = pageSize
		)
}