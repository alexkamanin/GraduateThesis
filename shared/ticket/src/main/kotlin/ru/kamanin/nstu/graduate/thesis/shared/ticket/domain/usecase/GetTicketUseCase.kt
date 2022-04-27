package ru.kamanin.nstu.graduate.thesis.shared.ticket.domain.usecase

import ru.kamanin.nstu.graduate.thesis.shared.exam.domain.entity.Exam
import ru.kamanin.nstu.graduate.thesis.shared.ticket.domain.entity.Task
import ru.kamanin.nstu.graduate.thesis.shared.ticket.domain.repository.TicketRepository
import javax.inject.Inject

class GetTicketUseCase @Inject constructor(
	private val repository: TicketRepository
) {

	suspend operator fun invoke(examId: Long, regulationRating: Exam.RegulationRating): List<Task> =
		repository.getTasks(examId, regulationRating)
}