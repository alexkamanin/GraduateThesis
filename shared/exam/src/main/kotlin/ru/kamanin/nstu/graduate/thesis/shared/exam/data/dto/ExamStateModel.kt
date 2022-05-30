package ru.kamanin.nstu.graduate.thesis.shared.exam.data.dto

import ru.kamanin.nstu.graduate.thesis.shared.exam.domain.entity.RatingState

data class ExamStateModel(
	val id: Long,
	val studentRatingState: RatingState
)