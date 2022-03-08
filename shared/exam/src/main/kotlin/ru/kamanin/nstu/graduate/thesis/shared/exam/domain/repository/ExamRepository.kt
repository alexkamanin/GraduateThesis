package ru.kamanin.nstu.graduate.thesis.shared.exam.domain.repository

import ru.kamanin.nstu.graduate.thesis.shared.exam.domain.entity.Exam

interface ExamRepository {

	suspend fun get(): List<Exam>
}