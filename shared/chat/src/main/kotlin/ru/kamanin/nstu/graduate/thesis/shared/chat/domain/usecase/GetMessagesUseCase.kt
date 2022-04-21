package ru.kamanin.nstu.graduate.thesis.shared.chat.domain.usecase

import androidx.paging.Pager
import androidx.paging.PagingData
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import ru.kamanin.nstu.graduate.thesis.component.core.coroutines.dispatcher.ioDispatcher
import ru.kamanin.nstu.graduate.thesis.shared.chat.data.api.ChatPagingSource
import ru.kamanin.nstu.graduate.thesis.shared.chat.data.api.ChatPagingSource.Companion.PAGING_CONFIG
import ru.kamanin.nstu.graduate.thesis.shared.chat.domain.entity.Message
import javax.inject.Inject

class GetMessagesUseCase @Inject constructor(
	@ioDispatcher private val ioDispatcher: CoroutineDispatcher,
	private val chatPagingSourceFactory: ChatPagingSource.Factory,
) {

	operator fun invoke(periodId: Long): Flow<PagingData<Message>> =
		Pager(PAGING_CONFIG) { chatPagingSourceFactory.create(periodId) }
			.flow
			.flowOn(ioDispatcher)
}