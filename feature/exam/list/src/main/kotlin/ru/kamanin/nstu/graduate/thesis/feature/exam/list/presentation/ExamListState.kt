package ru.kamanin.nstu.graduate.thesis.feature.exam.list.presentation

import ru.kamanin.nstu.graduate.thesis.shared.exam.domain.entity.Exam

sealed interface ExamListState {

	object Loading : ExamListState

	object NoContent : ExamListState

	data class Content(val examItems: List<Exam>) : ExamListState
}