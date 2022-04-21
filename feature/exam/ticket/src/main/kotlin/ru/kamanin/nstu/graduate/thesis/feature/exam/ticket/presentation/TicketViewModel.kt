package ru.kamanin.nstu.graduate.thesis.feature.exam.ticket.presentation

import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
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
import ru.kamanin.nstu.graduate.thesis.shared.exam.domain.entity.Answer
import ru.kamanin.nstu.graduate.thesis.shared.exam.domain.entity.Exam
import ru.kamanin.nstu.graduate.thesis.shared.exam.domain.repository.TicketRepository
import ru.kamanin.nstu.graduate.thesis.shared.session.domain.scenario.LogoutScenario
import javax.inject.Inject

//TODO заменить все названия Task на Answer

@HiltViewModel
class TicketViewModel @Inject constructor(
	private val logoutScenario: LogoutScenario,
	private val repository: TicketRepository, //todo добавить юзкейсы
	private val timeManager: TimeManager,
	private val errorConverter: ErrorConverter,
	savedStateHandle: SavedStateHandle,
) : ViewModel() {

	private val exam: Exam = requireNotNull(savedStateHandle[Exam::class.java.name])

	private val _state = MutableStateFlow<TicketState>(TicketState.Initial)
	val state: StateFlow<TicketState> get() = _state.asStateFlow()

	private val _remainingTimeEvent = MutableSharedFlow<RemainingTime>(replay = 1)
	val remainingTimeEvent: SharedFlow<RemainingTime> get() = _remainingTimeEvent

	private val _swipeRefreshEvent = MutableLiveState<Boolean>()
	val swipeRefreshEvent: LiveState<Boolean> get() = _swipeRefreshEvent.asLiveState()

	val eventDispatcher = EventDispatcher<EventListener>()

	interface EventListener {

		fun navigateToChat(args: Bundle)

		fun navigateToTask(args: Bundle)

		fun navigateToSignIn()
	}

	init {
		_state.value = TicketState.Loading
		loadTasks()
	}

	fun refresh() {
		loadTasks()
	}

	private fun loadTasks() {
		//TODO подумать об обновлении при возврате с чата

		getRemainingTime(exam.period.end, timeManager.currentTime)
			.onEach(_remainingTimeEvent::emit)
			.launchIn(viewModelScope)

		viewModelScope.launch(::handleError) {
			val answers = repository.getAnswers(exam.id, exam.regulationRating)
			_swipeRefreshEvent.invoke(false)
			_state.value = TicketState.Content(answers)
		}
	}

	private fun handleError(throwable: Throwable) {
		_swipeRefreshEvent.invoke(false)

		errorConverter.convert(throwable).let { errorState ->
			when (errorState) {
				ErrorState.NotAuthorized -> logout()

				else                     -> _state.value = TicketState.Error(errorState)
			}
		}
	}

	private fun logout() {
		viewModelScope.launch {
			logoutScenario()
			eventDispatcher.dispatchEvent { navigateToSignIn() }
		}
	}

	fun selectTask(answer: Answer) {
		val args = bundleOf(
			Answer::class.java.name to answer,
			Exam::class.java.name to exam
		)
		eventDispatcher.dispatchEvent { navigateToTask(args) }
	}

	fun openChat() {
		val args = bundleOf(Exam::class.java.name to exam)
		eventDispatcher.dispatchEvent { navigateToChat(args) }
	}
}