package ru.kamanin.nstu.graduate.thesis.shared.ticket.domain.repository

import ru.kamanin.nstu.graduate.thesis.shared.exam.domain.entity.Exam
import ru.kamanin.nstu.graduate.thesis.shared.ticket.domain.entity.Task

interface TicketRepository {

	suspend fun getTasks(examId: Long, regulationRating: Exam.RegulationRating): List<Task>
}