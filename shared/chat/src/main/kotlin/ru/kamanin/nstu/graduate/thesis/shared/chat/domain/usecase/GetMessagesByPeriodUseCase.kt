package ru.kamanin.nstu.graduate.thesis.shared.chat.domain.usecase

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import ru.kamanin.nstu.graduate.thesis.shared.chat.domain.entity.Message
import ru.kamanin.nstu.graduate.thesis.shared.chat.domain.repository.ChatRepository
import javax.inject.Inject

class GetMessagesByPeriodUseCase @Inject constructor(
	private val repository: ChatRepository
) {

	operator fun invoke(periodId: Long): Flow<PagingData<Message>> =
		repository.getMessages(periodId)
}