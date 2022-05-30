package ru.kamanin.nstu.graduate.thesis.shared.exam.domain.repository

import ru.kamanin.nstu.graduate.thesis.shared.exam.domain.entity.Exam
import ru.kamanin.nstu.graduate.thesis.shared.exam.domain.entity.RatingState

interface ExamRepository {

	suspend fun get(): List<Exam>

	suspend fun setState(id: Long, ratingState: RatingState)
}