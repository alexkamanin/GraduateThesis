package ru.kamanin.nstu.graduate.thesis.shared.exam.data.repository

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import ru.kamanin.nstu.graduate.thesis.component.core.coroutines.dispatcher.ioDispatcher
import ru.kamanin.nstu.graduate.thesis.shared.exam.data.api.TicketApi
import ru.kamanin.nstu.graduate.thesis.shared.exam.data.dto.AnswerDto
import ru.kamanin.nstu.graduate.thesis.shared.exam.data.mapper.toEntity
import ru.kamanin.nstu.graduate.thesis.shared.exam.domain.entity.Answer
import ru.kamanin.nstu.graduate.thesis.shared.exam.domain.repository.TicketRepository
import javax.inject.Inject

class TicketRepositoryImpl @Inject constructor(
	@ioDispatcher private val ioDispatcher: CoroutineDispatcher,
	private val api: TicketApi
) : TicketRepository {

	override suspend fun getAnswers(examId: Long): List<Answer> =
		withContext(ioDispatcher) {
			api.get(examId)
				.sortedBy(AnswerDto::id)
				.map(AnswerDto::toEntity)
		}
}