package ru.kamanin.nstu.graduate.thesis.feature.exam.ticket.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.kamanin.nstu.graduate.thesis.component.core.mvvm.lifecycle.EventDispatcher
import ru.kamanin.nstu.graduate.thesis.feature.exam.ticket.ui.model.TaskItem
import javax.inject.Inject

@HiltViewModel
class TicketViewModel @Inject constructor() : ViewModel() {

	private val _state = MutableStateFlow<TicketState>(TicketState.Loading)
	val state: StateFlow<TicketState> get() = _state.asStateFlow()

	val eventDispatcher = EventDispatcher<EventListener>()

	interface EventListener {

		fun navigateToChat()

		fun navigateToTask()
	}

	init {
		viewModelScope.launch {
			delay(500)

			_state.value = TicketState.Content(
				taskItems = listOf(
					TaskItem(0, "Вопрос 1", TaskItem.Status.CHECKING),
					TaskItem(1, "Вопрос 2", TaskItem.Status.CHECKING),
					TaskItem(2, "Вопрос 3", TaskItem.Status.REJECTED),
					TaskItem(3, "Вопрос 4", TaskItem.Status.REJECTED),
					TaskItem(4, "Вопрос 5", TaskItem.Status.REVISION),
					TaskItem(5, "Вопрос 6", TaskItem.Status.CHECKING),
					TaskItem(6, "Вопрос 7", TaskItem.Status.APPROVED),
					TaskItem(7, "Вопрос 8", TaskItem.Status.CHECKING),
					TaskItem(8, "Вопрос 9", TaskItem.Status.APPROVED),
					TaskItem(9, "Вопрос 10", TaskItem.Status.APPROVED),
					TaskItem(10, "Вопрос 11", TaskItem.Status.CHECKING),
					TaskItem(12, "Вопрос 12", TaskItem.Status.APPROVED),
					TaskItem(13, "Вопрос 13", TaskItem.Status.CHECKING),
					TaskItem(15, "Вопрос 14", TaskItem.Status.CHECKING),
					TaskItem(15, "Вопрос 15", TaskItem.Status.CHECKING),
					TaskItem(16, "Вопрос 16", TaskItem.Status.CHECKING)
				)
			)
		}
	}

	fun selectTask(taskItem: TaskItem) {
		Log.d("TEST_TECH", taskItem.toString())
		eventDispatcher.dispatchEvent { navigateToTask() }
	}
}