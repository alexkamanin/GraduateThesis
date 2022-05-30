package ru.kamanin.nstu.graduate.thesis.shared.exam.domain.repository

import ru.kamanin.nstu.graduate.thesis.shared.exam.data.api.Passing
import ru.kamanin.nstu.graduate.thesis.shared.exam.domain.entity.Exam

interface ExamRepository {

	suspend fun get(): List<Exam>

	suspend fun setState(state: Passing)
}