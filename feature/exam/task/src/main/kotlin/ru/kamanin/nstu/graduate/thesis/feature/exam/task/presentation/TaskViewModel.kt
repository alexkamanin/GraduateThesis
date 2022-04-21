package ru.kamanin.nstu.graduate.thesis.feature.exam.task.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.kamanin.nstu.graduate.thesis.component.core.time.RemainingTime
import ru.kamanin.nstu.graduate.thesis.component.core.time.TimeManager
import ru.kamanin.nstu.graduate.thesis.component.core.time.getRemainingTime
import ru.kamanin.nstu.graduate.thesis.shared.exam.domain.entity.Answer
import ru.kamanin.nstu.graduate.thesis.shared.exam.domain.entity.Exam
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(
	savedStateHandle: SavedStateHandle,
	private val timeManager: TimeManager
) : ViewModel() {

	val answer: Answer = requireNotNull(savedStateHandle[Answer::class.java.name])
	val exam: Exam = requireNotNull(savedStateHandle[Exam::class.java.name])

	private val _remainingTimeEvent = MutableSharedFlow<RemainingTime>(replay = 1)
	val remainingTimeEvent: SharedFlow<RemainingTime> get() = _remainingTimeEvent

	init {
		getRemainingTime(exam.period.end, timeManager.currentTime)
			.onEach(_remainingTimeEvent::emit)
			.launchIn(viewModelScope)
	}
}