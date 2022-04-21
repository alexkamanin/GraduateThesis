package ru.kamanin.nstu.graduate.thesis.shared.chat.data.api

import retrofit2.http.*
import ru.kamanin.nstu.graduate.thesis.shared.chat.data.dto.MessageDto
import ru.kamanin.nstu.graduate.thesis.shared.chat.data.paging.Paging
import ru.kamanin.nstu.graduate.thesis.shared.chat.domain.entity.MessageSummary

interface ChatApi {

	@GET("/periods/{periodId}/messages?sort=sendTime,DESC")
	suspend fun getMessages(
		@Path("periodId") periodId: Long,
		@Query("page") pageNumber: Int,
		@Query("size") pageSize: Int,
	): Paging<MessageDto>

	@POST("/periods/{periodId}/messages")
	suspend fun sendMessage(
		@Path("periodId") periodId: Long,
		@Body message: MessageSummary
	): MessageDto
}