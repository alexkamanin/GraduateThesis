package ru.kamanin.nstu.graduate.thesis.shared.exam.data.api

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import ru.kamanin.nstu.graduate.thesis.shared.exam.data.dto.ExamDto
import ru.kamanin.nstu.graduate.thesis.shared.exam.domain.entity.RatingState

interface ExamApi {

	@GET("/students/exam-infos")
	suspend fun get(): List<ExamDto>

	@PUT("/student-rating/state")
	suspend fun setPassingStatus(@Body passing: Passing)
}

data class Passing(
	val id: Long,
	val studentRatingState: RatingState
)