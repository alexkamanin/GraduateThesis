package ru.kamanin.nstu.graduate.thesis.shared.ticket.data.repository

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import ru.kamanin.nstu.graduate.thesis.shared.exam.domain.entity.Exam
import ru.kamanin.nstu.graduate.thesis.shared.ticket.data.api.TicketApi
import ru.kamanin.nstu.graduate.thesis.shared.ticket.data.dto.TaskDto
import ru.kamanin.nstu.graduate.thesis.shared.ticket.data.mapper.TaskMapper
import ru.kamanin.nstu.graduate.thesis.shared.ticket.domain.entity.Task
import ru.kamanin.nstu.graduate.thesis.shared.ticket.domain.repository.TicketRepository
import ru.kamanin.nstu.graduate.thesis.utils.coroutines.dispatcher.ioDispatcher
import javax.inject.Inject

class TicketRepositoryImpl @Inject constructor(
	@ioDispatcher private val ioDispatcher: CoroutineDispatcher,
	private val api: TicketApi
) : TicketRepository {

	override suspend fun getTasks(examId: Long, regulationRating: Exam.RegulationRating): List<Task> =
		withContext(ioDispatcher) {
			api.getTasks(examId)
				.sortedBy(TaskDto::id)
				.map { answerDto -> TaskMapper.convert(answerDto, regulationRating) }
		}
}