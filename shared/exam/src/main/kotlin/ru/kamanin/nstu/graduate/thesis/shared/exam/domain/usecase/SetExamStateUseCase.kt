package ru.kamanin.nstu.graduate.thesis.shared.exam.domain.usecase

import ru.kamanin.nstu.graduate.thesis.shared.exam.domain.entity.RatingState
import ru.kamanin.nstu.graduate.thesis.shared.exam.domain.repository.ExamRepository
import javax.inject.Inject

class SetExamStateUseCase @Inject constructor(
	private val repository: ExamRepository
) {

	suspend operator fun invoke(id: Long, ratingState: RatingState) {
		repository.setState(id, ratingState)
	}
}