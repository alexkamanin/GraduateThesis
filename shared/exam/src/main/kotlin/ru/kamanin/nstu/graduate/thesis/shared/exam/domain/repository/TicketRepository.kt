package ru.kamanin.nstu.graduate.thesis.shared.exam.domain.repository

import ru.kamanin.nstu.graduate.thesis.shared.exam.domain.entity.Answer

interface TicketRepository {

	suspend fun getAnswers(examId: Long): List<Answer>
}