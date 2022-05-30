package ru.kamanin.nstu.graduate.thesis.shared.exam.data.repository

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import ru.kamanin.nstu.graduate.thesis.shared.exam.data.api.ExamApi
import ru.kamanin.nstu.graduate.thesis.shared.exam.data.dto.ExamDto
import ru.kamanin.nstu.graduate.thesis.shared.exam.data.dto.ExamStateModel
import ru.kamanin.nstu.graduate.thesis.shared.exam.data.mapper.toEntity
import ru.kamanin.nstu.graduate.thesis.shared.exam.domain.entity.Exam
import ru.kamanin.nstu.graduate.thesis.shared.exam.domain.entity.RatingState
import ru.kamanin.nstu.graduate.thesis.shared.exam.domain.repository.ExamRepository
import ru.kamanin.nstu.graduate.thesis.utils.coroutines.dispatcher.ioDispatcher
import javax.inject.Inject

class ExamRepositoryImpl @Inject constructor(
	@ioDispatcher private val ioDispatcher: CoroutineDispatcher,
	private val api: ExamApi
) : ExamRepository {

	override suspend fun get(): List<Exam> =
		withContext(ioDispatcher) {
			api.get().map(ExamDto::toEntity)
		}

	override suspend fun setState(id: Long, ratingState: RatingState) {
		withContext(ioDispatcher) {
			api.setState(ExamStateModel(id, ratingState))
		}
	}
}