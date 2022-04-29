package ru.kamanin.nstu.graduate.thesis.feature.exam.task.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import ru.kamanin.nstu.graduate.thesis.shared.account.domain.usecase.GetPersonalAccountUseCase
import ru.kamanin.nstu.graduate.thesis.shared.artefact.domain.delegate.ArtefactViewModelDelegate
import ru.kamanin.nstu.graduate.thesis.shared.artefact.domain.usecase.GetArtefactMetaDataUseCase
import ru.kamanin.nstu.graduate.thesis.shared.chat.presentation.model.MessageItem
import ru.kamanin.nstu.graduate.thesis.shared.exam.domain.entity.Exam
import ru.kamanin.nstu.graduate.thesis.shared.ticket.domain.entity.Task
import ru.kamanin.nstu.graduate.thesis.shared.ticket.domain.usecase.GetMessagesByTaskUseCase
import ru.kamanin.nstu.graduate.thesis.utils.coroutines.flow.LiveEvent
import ru.kamanin.nstu.graduate.thesis.utils.coroutines.flow.MutableLiveEvent
import ru.kamanin.nstu.graduate.thesis.utils.coroutines.flow.asLiveEvent
import ru.kamanin.nstu.graduate.thesis.utils.paging.mapPaging
import ru.kamanin.nstu.graduate.thesis.utils.time.RemainingTime
import ru.kamanin.nstu.graduate.thesis.utils.time.TimeManager
import ru.kamanin.nstu.graduate.thesis.utils.time.getRemainingTime
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(
	private val getArtefactMetaDataUseCase: GetArtefactMetaDataUseCase,
	private val getPersonalAccountUseCase: GetPersonalAccountUseCase,
	private val getMessagesByTaskUseCase: GetMessagesByTaskUseCase,
	private val timeManager: TimeManager,
	artefactViewModelDelegate: ArtefactViewModelDelegate,
	savedStateHandle: SavedStateHandle,
) : ViewModel(), ArtefactViewModelDelegate by artefactViewModelDelegate {

	private companion object {

		const val EMPTY_TEXT = ""
	}

	val task: Task = requireNotNull(savedStateHandle[Task::class.java.name])
	val exam: Exam = requireNotNull(savedStateHandle[Exam::class.java.name])

	val message = MutableStateFlow(EMPTY_TEXT)

	private val _state = MutableStateFlow(computeInitialState())
	val state: StateFlow<TaskState> get() = _state.asStateFlow()

	private val _sendMessageEvent = MutableLiveEvent()
	val sendMessageEvent: LiveEvent get() = _sendMessageEvent.asLiveEvent()

	private val _remainingTimeEvent = MutableSharedFlow<RemainingTime>(replay = 1)
	val remainingTimeEvent: SharedFlow<RemainingTime> get() = _remainingTimeEvent

	init {
		getRemainingTime(exam.period.end, timeManager.currentTime)
			.onEach(_remainingTimeEvent::emit)
			.launchIn(viewModelScope)
	}

	private fun computeInitialState(): TaskState =
		TaskState(task = task, messages = PagingData.empty())

	fun loadMessages() {
		viewModelScope.launch {

			val personalAccount = getPersonalAccountUseCase()
			val teacherAccount = exam.teacher.account

			getMessagesByTaskUseCase(exam.period.id)
				.cachedIn(viewModelScope)
				.mapPaging { message ->
					MessageItem.from(
						message = message,
						personalAccount = personalAccount,
						otherAccount = teacherAccount,
						loadArtefact = getArtefactMetaDataUseCase::invoke
					)
				}
				.collectLatest {
					_state.value = _state.value.copy(messages = it)
				}
		}
	}

	fun send() {
		//TODO("Not yet implemented")
	}
}