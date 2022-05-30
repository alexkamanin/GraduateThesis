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
import ru.kamanin.nstu.graduate.thesis.shared.artefact.domain.entity.ArtefactMetaData
import ru.kamanin.nstu.graduate.thesis.shared.artefact.domain.usecase.GetArtefactMetaDataUseCase
import ru.kamanin.nstu.graduate.thesis.shared.artefact.domain.usecase.UploadArtefactUseCase
import ru.kamanin.nstu.graduate.thesis.shared.chat.presentation.model.MessageItem
import ru.kamanin.nstu.graduate.thesis.shared.clipdata.di.CopiedText
import ru.kamanin.nstu.graduate.thesis.shared.clipdata.domain.usecase.SetClipDataUseCase
import ru.kamanin.nstu.graduate.thesis.shared.exam.domain.entity.Exam
import ru.kamanin.nstu.graduate.thesis.shared.ticket.domain.entity.Task
import ru.kamanin.nstu.graduate.thesis.shared.ticket.domain.usecase.GetMessagesByTaskUseCase
import ru.kamanin.nstu.graduate.thesis.shared.ticket.domain.usecase.SendMessageByTaskUseCase
import ru.kamanin.nstu.graduate.thesis.utils.coroutines.exception.launch
import ru.kamanin.nstu.graduate.thesis.utils.coroutines.flow.*
import ru.kamanin.nstu.graduate.thesis.utils.paging.mapPaging
import ru.kamanin.nstu.graduate.thesis.utils.time.RemainingTime
import ru.kamanin.nstu.graduate.thesis.utils.time.TimeManager
import ru.kamanin.nstu.graduate.thesis.utils.time.getRemainingTime
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(
	private val uploadArtefactUseCase: UploadArtefactUseCase,
	private val getArtefactMetaDataUseCase: GetArtefactMetaDataUseCase,
	private val getPersonalAccountUseCase: GetPersonalAccountUseCase,
	private val getMessagesByTaskUseCase: GetMessagesByTaskUseCase,
	private val setClipDataUseCase: SetClipDataUseCase,
	private val sentMessagesByTaskUseCase: SendMessageByTaskUseCase,
	timeManager: TimeManager,
	artefactViewModelDelegate: ArtefactViewModelDelegate,
	savedStateHandle: SavedStateHandle,
	@CopiedText private val copiedText: String
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

	private val _infoEvent = MutableLiveState<String>()
	val infoEvent: LiveState<String> get() = _infoEvent

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

			getMessagesByTaskUseCase(task.id)
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
		if (sendingAvailable()) {
			viewModelScope.launch(::handleSentMessageError) {
				val artefact = getArtefact()
				val message = getMessage()

				sentMessagesByTaskUseCase(task.id, message, artefact?.id)

				clearMessage()
				clearAttachedFile()

				_sendMessageEvent()
			}
		}
	}

	private fun handleSentMessageError(throwable: Throwable) {

	}

	private fun sendingAvailable(): Boolean =
		message.value.isNotEmpty() || attachedFile != null

	private suspend fun getArtefact(): ArtefactMetaData? =
		if (attachedFile != null) uploadArtefactUseCase(attachedFile) else null

	private fun getMessage(): String? =
		message.value.takeIf(String::isNotEmpty)

	private fun clearMessage() {
		message.value = EMPTY_TEXT
	}

	fun copyText(text: String) {
		setClipDataUseCase(text)

		_infoEvent.invoke(copiedText)
	}
}