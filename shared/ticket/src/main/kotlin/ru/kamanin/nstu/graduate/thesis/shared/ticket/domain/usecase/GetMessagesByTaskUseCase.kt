package ru.kamanin.nstu.graduate.thesis.shared.ticket.domain.usecase

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import ru.kamanin.nstu.graduate.thesis.shared.chat.domain.entity.Message
import ru.kamanin.nstu.graduate.thesis.shared.ticket.domain.repository.TaskRepository
import javax.inject.Inject

class GetMessagesByTaskUseCase @Inject constructor(
	private val repository: TaskRepository
) {

	operator fun invoke(answerId: Long): Flow<PagingData<Message>> =
		repository.getMessages(answerId)
}