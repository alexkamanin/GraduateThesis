package ru.kamanin.nstu.graduate.thesis.feature.exam.list.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.kamanin.nstu.graduate.thesis.component.core.coroutines.flow.LiveState
import ru.kamanin.nstu.graduate.thesis.component.core.coroutines.flow.MutableLiveState
import ru.kamanin.nstu.graduate.thesis.component.core.coroutines.flow.asLiveState
import ru.kamanin.nstu.graduate.thesis.component.core.coroutines.flow.invoke
import ru.kamanin.nstu.graduate.thesis.component.core.mvvm.lifecycle.EventDispatcher
import ru.kamanin.nstu.graduate.thesis.shared.exam.domain.entity.Exam
import ru.kamanin.nstu.graduate.thesis.shared.exam.domain.usecase.GetExamListUseCase
import ru.kamanin.nstu.graduate.thesis.shared.session.domain.usecase.LogoutUseCase
import javax.inject.Inject

@HiltViewModel
class ExamListViewModel @Inject constructor(
	private val getExamListUseCase: GetExamListUseCase,
	private val logoutUseCase: LogoutUseCase,
) : ViewModel() {

	private val _state = MutableStateFlow<ExamListState>(ExamListState.Loading)
	val state: StateFlow<ExamListState> get() = _state.asStateFlow()

	private val _swipeRefreshEvent = MutableLiveState<Boolean>()
	val swipeRefreshEvent: LiveState<Boolean> get() = _swipeRefreshEvent.asLiveState()

	val eventDispatcher = EventDispatcher<EventListener>()

	interface EventListener {

		fun navigateToSignIn()

		fun navigateToTicket()
	}

	init {
		loadExams()
	}

	fun refresh() {
		loadExams()
	}

	private fun loadExams() {
		viewModelScope.launch {
			runCatching {
				val examList = getExamListUseCase()
				if (examList.isEmpty()) {
					_state.value = ExamListState.NoContent
				} else {
					_state.value = ExamListState.Content(examList)
				}
			}.onFailure(::handleLoadingExamError)
			_swipeRefreshEvent.invoke(false)
		}
	}

	private fun handleLoadingExamError(throwable: Throwable) {
		Log.d("TEST_TECH", throwable.toString())
	}

	fun selectExam(exam: Exam) {
		eventDispatcher.dispatchEvent { navigateToTicket() }
	}

	fun logout() {
		viewModelScope.launch {
			logoutUseCase()
			eventDispatcher.dispatchEvent { navigateToSignIn() }
		}
	}
}