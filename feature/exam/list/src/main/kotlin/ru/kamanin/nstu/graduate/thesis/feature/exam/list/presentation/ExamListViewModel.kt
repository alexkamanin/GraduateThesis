package ru.kamanin.nstu.graduate.thesis.feature.exam.list.presentation

import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.kamanin.nstu.graduate.thesis.feature.exam.list.presentation.model.ExamFilter
import ru.kamanin.nstu.graduate.thesis.shared.exam.domain.entity.Exam
import ru.kamanin.nstu.graduate.thesis.shared.exam.domain.entity.ExamState
import ru.kamanin.nstu.graduate.thesis.shared.exam.domain.usecase.GetExamListUseCase
import ru.kamanin.nstu.graduate.thesis.shared.session.domain.scenario.LogoutScenario
import ru.kamanin.nstu.graduate.thesis.utils.coroutines.event.EventDispatcher
import ru.kamanin.nstu.graduate.thesis.utils.coroutines.exception.launch
import ru.kamanin.nstu.graduate.thesis.utils.coroutines.flow.LiveState
import ru.kamanin.nstu.graduate.thesis.utils.coroutines.flow.MutableLiveState
import ru.kamanin.nstu.graduate.thesis.utils.coroutines.flow.asLiveState
import ru.kamanin.nstu.graduate.thesis.utils.coroutines.flow.invoke
import ru.kamanin.nstu.graduate.thesis.utils.error.ErrorConverter
import ru.kamanin.nstu.graduate.thesis.utils.error.ErrorState
import ru.kamanin.nstu.graduate.thesis.utils.time.TimeManager
import javax.inject.Inject

@HiltViewModel
class ExamListViewModel @Inject constructor(
	private val getExamListUseCase: GetExamListUseCase,
	private val logoutScenario: LogoutScenario,
	private val timeManager: TimeManager,
	private val errorConverter: ErrorConverter
) : ViewModel() {

	private companion object {

		val EXAM_COMPLETED_STATUSES = listOf(ExamState.FINISHED, ExamState.CLOSED)
	}

	private var selectedFilter = ExamFilter.ACTIVE

	private val _state = MutableStateFlow<ExamListState>(ExamListState.Initial)
	val state: StateFlow<ExamListState> get() = _state.asStateFlow()

	private val _swipeRefreshEvent = MutableLiveState<Boolean>()
	val swipeRefreshEvent: LiveState<Boolean> get() = _swipeRefreshEvent.asLiveState()

	val eventDispatcher = EventDispatcher<EventListener>()

	interface EventListener {

		fun navigateToSignIn()

		fun navigateToTicket(args: Bundle)
	}

	init {
		_state.value = ExamListState.Loading
		loadExams()
	}

	fun refresh() {
		loadExams()
	}

	private fun loadExams() {
		viewModelScope.launch(::handleError) {
			_state.value = getState()
			_swipeRefreshEvent.invoke(false)
		}
	}

	private fun handleError(throwable: Throwable) {
		_swipeRefreshEvent.invoke(false)

		errorConverter.convert(throwable).let { errorState ->
			if (errorState == ErrorState.NotAuthorized) {
				logout()
			} else {
				_state.value = ExamListState.Error(errorState)
			}
		}
	}

	fun selectFilter(filter: ExamFilter) {
		selectedFilter = filter

		_state.value = ExamListState.Loading
		loadExams()
	}

	private suspend fun getState(): ExamListState {
		val filteredExams = getFilteredExams()

		return if (filteredExams.isEmpty()) {
			ExamListState.NoContent
		} else {
			ExamListState.Content(
				exams = getFilteredExams(),
				filter = selectedFilter
			)
		}
	}

	private suspend fun getFilteredExams(): List<Exam> {
		val examList = getExamListUseCase()

		return when (selectedFilter) {

			ExamFilter.ACTIVE   -> {
				examList
					.filter { it.examState !in EXAM_COMPLETED_STATUSES }
					.sortedBy { it.period.start }
			}

			ExamFilter.INACTIVE -> {
				examList
					.filter { it.examState in EXAM_COMPLETED_STATUSES }
					.sortedByDescending { it.period.start }
			}
		}
	}

	fun selectExam(exam: Exam) {
		eventDispatcher.dispatchEvent {
			val args = bundleOf(Exam::class.java.name to exam)
			navigateToTicket(args)
		}
	}

	fun logout() {
		viewModelScope.launch {
			logoutScenario()
			eventDispatcher.dispatchEvent { navigateToSignIn() }
		}
	}
}