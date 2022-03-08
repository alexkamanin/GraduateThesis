package ru.kamanin.nstu.graduate.thesis.shared.exam.data.api

import retrofit2.http.GET
import ru.kamanin.nstu.graduate.thesis.shared.exam.data.dto.ExamDto

interface ExamApi {

	@GET("/student/ticket")
	suspend fun get(): List<ExamDto>
}