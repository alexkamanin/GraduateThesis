package ru.kamanin.nstu.graduate.thesis.shared.exam.domain.usecase

import ru.kamanin.nstu.graduate.thesis.shared.exam.domain.entity.Exam
import ru.kamanin.nstu.graduate.thesis.shared.exam.domain.repository.ExamRepository
import javax.inject.Inject

class GetExamListUseCase @Inject constructor(
	private val repository: ExamRepository
) {

	suspend operator fun invoke(): List<Exam> =
		repository.get()
}