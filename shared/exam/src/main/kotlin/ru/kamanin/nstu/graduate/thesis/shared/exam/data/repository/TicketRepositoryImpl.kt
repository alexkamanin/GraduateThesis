package ru.kamanin.nstu.graduate.thesis.shared.exam.data.repository

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import ru.kamanin.nstu.graduate.thesis.component.core.coroutines.dispatcher.ioDispatcher
import ru.kamanin.nstu.graduate.thesis.shared.exam.data.api.TicketApi
import ru.kamanin.nstu.graduate.thesis.shared.exam.data.dto.AnswerDto
import ru.kamanin.nstu.graduate.thesis.shared.exam.data.mapper.AnswerMapper
import ru.kamanin.nstu.graduate.thesis.shared.exam.domain.entity.Answer
import ru.kamanin.nstu.graduate.thesis.shared.exam.domain.entity.Exam
import ru.kamanin.nstu.graduate.thesis.shared.exam.domain.repository.TicketRepository
import javax.inject.Inject

class TicketRepositoryImpl @Inject constructor(
	@ioDispatcher private val ioDispatcher: CoroutineDispatcher,
	private val api: TicketApi
) : TicketRepository {

	override suspend fun getAnswers(examId: Long, regulationRating: Exam.RegulationRating): List<Answer> =
		withContext(ioDispatcher) {
			api.get(examId)
				.sortedBy(AnswerDto::id)
				.map { answerDto -> AnswerMapper.convert(answerDto, regulationRating) }
		}
}