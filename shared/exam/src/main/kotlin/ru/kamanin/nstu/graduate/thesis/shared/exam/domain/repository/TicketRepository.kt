package ru.kamanin.nstu.graduate.thesis.shared.exam.domain.repository

import ru.kamanin.nstu.graduate.thesis.shared.exam.domain.entity.Answer
import ru.kamanin.nstu.graduate.thesis.shared.exam.domain.entity.Exam

interface TicketRepository {

	suspend fun getAnswers(examId: Long, regulationRating: Exam.RegulationRating): List<Answer>
}