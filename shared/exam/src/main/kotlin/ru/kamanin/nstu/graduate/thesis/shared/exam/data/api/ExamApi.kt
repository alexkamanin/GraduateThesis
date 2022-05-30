package ru.kamanin.nstu.graduate.thesis.shared.exam.data.api

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import ru.kamanin.nstu.graduate.thesis.shared.exam.data.dto.ExamDto
import ru.kamanin.nstu.graduate.thesis.shared.exam.data.dto.ExamStateModel

interface ExamApi {

	@GET("/students/exam-infos")
	suspend fun get(): List<ExamDto>

	@PUT("/student-rating/state")
	suspend fun setState(@Body state: ExamStateModel)
}