package ru.kamanin.nstu.graduate.thesis.feature.exam.chat.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import ru.kamanin.nstu.graduate.thesis.shared.account.domain.usecase.GetPersonalAccountUseCase
import ru.kamanin.nstu.graduate.thesis.shared.artefact.domain.delegate.ArtefactViewModelDelegate
import ru.kamanin.nstu.graduate.thesis.shared.artefact.domain.entity.ArtefactMetaData
import ru.kamanin.nstu.graduate.thesis.shared.artefact.domain.usecase.GetArtefactMetaDataUseCase
import ru.kamanin.nstu.graduate.thesis.shared.artefact.domain.usecase.UploadArtefactUseCase
import ru.kamanin.nstu.graduate.thesis.shared.chat.domain.usecase.GetMessagesByPeriodUseCase
import ru.kamanin.nstu.graduate.thesis.shared.chat.domain.usecase.SendMessageByPeriodUseCase
import ru.kamanin.nstu.graduate.thesis.shared.chat.presentation.model.MessageItem
import ru.kamanin.nstu.graduate.thesis.shared.exam.domain.entity.Exam
import ru.kamanin.nstu.graduate.thesis.utils.coroutines.flow.LiveEvent
import ru.kamanin.nstu.graduate.thesis.utils.coroutines.flow.MutableLiveEvent
import ru.kamanin.nstu.graduate.thesis.utils.coroutines.flow.asLiveEvent
import ru.kamanin.nstu.graduate.thesis.utils.coroutines.flow.invoke
import ru.kamanin.nstu.graduate.thesis.utils.paging.mapPaging
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
	private val uploadArtefactUseCase: UploadArtefactUseCase,
	private val getArtefactMetaDataUseCase: GetArtefactMetaDataUseCase,
	private val getPersonalAccountUseCase: GetPersonalAccountUseCase,
	private val getMessagesByPeriodUseCase: GetMessagesByPeriodUseCase,
	private val sendMessageByPeriodUseCase: SendMessageByPeriodUseCase,
	artefactViewModelDelegate: ArtefactViewModelDelegate,
	savedStateHandle: SavedStateHandle,
) : ViewModel(), ArtefactViewModelDelegate by artefactViewModelDelegate {

	private companion object {

		const val EMPTY_TEXT = ""
	}

	private val exam: Exam = requireNotNull(savedStateHandle[Exam::class.java.name])

	val message = MutableStateFlow(EMPTY_TEXT)

	private val _state = MutableStateFlow<ChatState>(ChatState.Initial)
	val state: StateFlow<ChatState> get() = _state.asStateFlow()

	private val _sendMessageEvent = MutableLiveEvent()
	val sendMessageEvent: LiveEvent get() = _sendMessageEvent.asLiveEvent()

	init {
		_state.value = ChatState.Loading

		viewModelScope.launch {

			val personalAccount = getPersonalAccountUseCase()
			val teacherAccount = exam.teacher.account

			getMessagesByPeriodUseCase(exam.period.id)
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
					_state.value = ChatState.Content(it)
				}
		}
	}

	fun send() {
		if (sendingAvailable()) {
			viewModelScope.launch {
				val artefact = getArtefact()
				val message = getMessage()

				sendMessageByPeriodUseCase(exam.period.id, message, artefact?.id)

				clearMessage()
				clearAttachedFile()

				_sendMessageEvent()
			}
		}
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
}