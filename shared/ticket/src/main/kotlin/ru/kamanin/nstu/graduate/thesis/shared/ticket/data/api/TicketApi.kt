package ru.kamanin.nstu.graduate.thesis.shared.ticket.data.api

import retrofit2.http.*
import ru.kamanin.nstu.graduate.thesis.shared.chat.data.dto.MessageDto
import ru.kamanin.nstu.graduate.thesis.shared.chat.domain.entity.MessageSummary
import ru.kamanin.nstu.graduate.thesis.shared.ticket.data.dto.TaskDto
import ru.kamanin.nstu.graduate.thesis.utils.paging.Paging

interface TicketApi {

	@GET("/student-rating/{examId}/answer")
	suspend fun getTasks(@Path("examId") examId: Long): List<TaskDto>

	@GET("/answers/{answerId}/message?sort=sendTime,DESC")
	suspend fun getMessages(
		@Path("answerId") answerId: Long,
		@Query("page") pageNumber: Int,
		@Query("size") pageSize: Int,
	): Paging<MessageDto>

	@POST("/answers/{answerId}/message")
	suspend fun sendMessage(
		@Path("answerId") answerId: Long,
		@Body message: MessageSummary
	): MessageDto
}