package ru.kamanin.nstu.graduate.thesis.shared.exam.data.api

import retrofit2.http.GET
import retrofit2.http.Path
import ru.kamanin.nstu.graduate.thesis.shared.exam.data.dto.AnswerDto

interface TicketApi {

	@GET("/ticket/{examId}/answer")
	suspend fun get(@Path("examId") examId: Long): List<AnswerDto>
}