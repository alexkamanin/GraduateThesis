package ru.kamanin.nstu.graduate.thesis.feature.exam.ticket.presentation

import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import ru.kamanin.nstu.graduate.thesis.component.core.coroutines.exception.launch
import ru.kamanin.nstu.graduate.thesis.component.core.coroutines.flow.LiveState
import ru.kamanin.nstu.graduate.thesis.component.core.coroutines.flow.MutableLiveState
import ru.kamanin.nstu.graduate.thesis.component.core.coroutines.flow.asLiveState
import ru.kamanin.nstu.graduate.thesis.component.core.coroutines.flow.invoke
import ru.kamanin.nstu.graduate.thesis.component.core.error.ErrorConverter
import ru.kamanin.nstu.graduate.thesis.component.core.error.ErrorState
import ru.kamanin.nstu.graduate.thesis.component.core.mvvm.lifecycle.EventDispatcher
import ru.kamanin.nstu.graduate.thesis.component.core.time.RemainingTime
import ru.kamanin.nstu.graduate.thesis.component.core.time.TimeManager
import ru.kamanin.nstu.graduate.thesis.component.core.time.getRemainingTime
import ru.kamanin.nstu.graduate.thesis.feature.exam.ticket.ui.model.TaskItem
import ru.kamanin.nstu.graduate.thesis.shared.exam.domain.entity.Exam
import ru.kamanin.nstu.graduate.thesis.shared.exam.domain.entity.TaskType
import ru.kamanin.nstu.graduate.thesis.shared.exam.domain.repository.TicketRepository
import javax.inject.Inject

@HiltViewModel
class TicketViewModel @Inject constructor(
	savedStateHandle: SavedStateHandle,
	private val repository: TicketRepository,
	private val timeManager: TimeManager,
	private val errorConverter: ErrorConverter
) : ViewModel() {

	private val examId: Long = requireNotNull(savedStateHandle["examId"])

	private val period: Exam.Period = requireNotNull(savedStateHandle["period"])

	private val _state = MutableStateFlow<TicketState>(TicketState.Initial)
	val state: StateFlow<TicketState> get() = _state.asStateFlow()

	val eventDispatcher = EventDispatcher<EventListener>()

	private val _remainingTimeEvent = MutableSharedFlow<RemainingTime>(replay = 1)
	val remainingTimeEvent: SharedFlow<RemainingTime> get() = _remainingTimeEvent

	private val _errorEvent = MutableLiveState<ErrorState>()
	val errorEvent: LiveState<ErrorState> get() = _errorEvent.asLiveState()

	interface EventListener {

		fun navigateToChat()

		fun navigateToTask(args: Bundle)
	}

	init {
		_state.value = TicketState.Loading

		getRemainingTime(period.end, timeManager.currentTime)
			.onEach(_remainingTimeEvent::emit)
			.launchIn(viewModelScope)

		viewModelScope.launch(::handleError) {
			val answers = repository.getAnswers(examId)

			val questionCount = answers.count { it.taskType == TaskType.QUESTION }
			val exerciseCount = answers.count { it.taskType == TaskType.EXERCISE }

			val taskItems = answers.mapIndexed { index, answer ->
				TaskItem(
					id = answer.id,
					number = when (answer.taskType) {
						TaskType.EXERCISE -> index % exerciseCount + 1
						TaskType.QUESTION -> index % questionCount + 1
					},
					text = answer.description,
					theme = answer.theme,
					taskType = answer.taskType,
					status = TaskItem.Status.CHECKING
				)
			}
			_state.value = TicketState.Content(taskItems)
		}
	}

	private fun handleError(throwable: Throwable) {
		val error = errorConverter.convert(throwable)
		_errorEvent(error)
	}

	fun selectTask(taskItem: TaskItem) {
		val args = bundleOf(
			"text" to taskItem.text,
			"theme" to taskItem.theme,
			"period" to period
		)
		eventDispatcher.dispatchEvent { navigateToTask(args) }
	}

	fun openChat() {
		eventDispatcher.dispatchEvent { navigateToChat() }
	}
}