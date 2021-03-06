package ru.kamanin.nstu.graduate.thesis.feature.exam.list.presentation

import ru.kamanin.nstu.graduate.thesis.feature.exam.list.presentation.model.ExamFilter
import ru.kamanin.nstu.graduate.thesis.shared.exam.domain.entity.Exam
import ru.kamanin.nstu.graduate.thesis.utils.error.ErrorState

sealed interface ExamListState {

	object Initial : ExamListState

	object Loading : ExamListState

	object NoContent : ExamListState

	data class Content(
		val exams: List<Exam>,
		val filter: ExamFilter
	) : ExamListState

	data class Error(val errorState: ErrorState) : ExamListState
}