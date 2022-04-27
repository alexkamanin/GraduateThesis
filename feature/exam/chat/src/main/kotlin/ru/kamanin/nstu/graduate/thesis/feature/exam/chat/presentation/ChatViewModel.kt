package ru.kamanin.nstu.graduate.thesis.feature.exam.chat.presentation

import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
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
import ru.kamanin.nstu.graduate.thesis.feature.exam.chat.presentation.model.MessageItem
import ru.kamanin.nstu.graduate.thesis.shared.account.domain.usecase.GetPersonalAccountUseCase
import ru.kamanin.nstu.graduate.thesis.shared.artefact.domain.entity.Artefact
import ru.kamanin.nstu.graduate.thesis.shared.artefact.domain.entity.FileInfo
import ru.kamanin.nstu.graduate.thesis.shared.artefact.domain.repository.FileInfoRepository
import ru.kamanin.nstu.graduate.thesis.shared.artefact.domain.usecase.DownloadArtefactUseCase
import ru.kamanin.nstu.graduate.thesis.shared.artefact.domain.usecase.GetArtefactUseCase
import ru.kamanin.nstu.graduate.thesis.shared.artefact.domain.usecase.UploadArtefactUseCase
import ru.kamanin.nstu.graduate.thesis.shared.chat.domain.usecase.GetMessagesByPeriodUseCase
import ru.kamanin.nstu.graduate.thesis.shared.chat.domain.usecase.SendMessageByPeriodUseCase
import ru.kamanin.nstu.graduate.thesis.shared.exam.domain.entity.Exam
import ru.kamanin.nstu.graduate.thesis.utils.coroutines.exception.launch
import ru.kamanin.nstu.graduate.thesis.utils.coroutines.flow.LiveState
import ru.kamanin.nstu.graduate.thesis.utils.coroutines.flow.MutableLiveState
import ru.kamanin.nstu.graduate.thesis.utils.coroutines.flow.asLiveState
import ru.kamanin.nstu.graduate.thesis.utils.coroutines.flow.invoke
import ru.kamanin.nstu.graduate.thesis.utils.paging.mapPaging
import javax.inject.Inject

//TODO при скачивании файла кидать пользователю пуш, при клике открывать файл

@HiltViewModel
class ChatViewModel @Inject constructor(
	private val uploadArtefactUseCase: UploadArtefactUseCase,
	private val downloadArtefactUseCase: DownloadArtefactUseCase,
	private val getArtefactUseCase: GetArtefactUseCase,
	private val getPersonalAccountUseCase: GetPersonalAccountUseCase,
	private val getMessagesByPeriodUseCase: GetMessagesByPeriodUseCase,
	private val sendMessageByPeriodUseCase: SendMessageByPeriodUseCase,
	private val fileInfoRepository: FileInfoRepository,
	savedStateHandle: SavedStateHandle,
) : ViewModel() {

	private companion object {

		const val EMPTY_TEXT = ""
	}

	private val exam: Exam = requireNotNull(savedStateHandle[Exam::class.java.name])

	private val _state = MutableStateFlow<ChatState>(ChatState.Initial)
	val state: StateFlow<ChatState> get() = _state.asStateFlow()

	private val _sendEvent = MutableLiveState<Unit>()
	val sendEvent: LiveState<Unit> = _sendEvent.asLiveState()

	private val _fileAttachedEvent = MutableLiveState<FileInfo?>()
	val fileAttachedEvent: LiveState<FileInfo?> get() = _fileAttachedEvent.asLiveState()

	private var file: FileInfo? = null
	private var uri: Uri? = null
	val message = MutableStateFlow(EMPTY_TEXT)

	init {
		_state.value = ChatState.Loading

		viewModelScope.launch {

			val personalAccount = getPersonalAccountUseCase()
			val teacherAccount = exam.teacher.account

			getMessagesByPeriodUseCase(5165)
				.cachedIn(viewModelScope)
				.mapPaging { message ->
					MessageItem.from(
						message = message,
						personalAccount = personalAccount,
						otherAccount = teacherAccount,
						loadArtefact = getArtefactUseCase::invoke
					)
				}
				.collectLatest {
					_state.value = ChatState.Content(it)
				}
		}
	}

	fun attachContent(uri: Uri) {
		this.uri = uri
		viewModelScope.launch(::handleArtefactLoadError) {
			file = fileInfoRepository.get(uri)
			_fileAttachedEvent(file)
		}
	}

	private fun handleArtefactLoadError(throwable: Throwable) {
		Log.e("TEST_TECH", throwable.stackTraceToString())
	}

	fun attachImage(bitmap: Bitmap) {
		Log.d("TEST_TECH", bitmap.toString())
	}

	fun openArtefact(artefact: Artefact) {
		Log.d("TEST_TECH", artefact.toString())

//		viewModelScope.launch {
//			downloadArtefactUseCase(5142)
//		}
	}

	fun send() {
		if (message.value.isNotEmpty() || uri != null) {
			viewModelScope.launch {
				val artefact = if (uri != null) {
					uploadArtefactUseCase(requireNotNull(uri))
				} else {
					null
				}
				sendMessageByPeriodUseCase(5165, message.value.takeIf { it.isNotEmpty() }, artefact?.id)
				message.value = ""
				uri = null
				_sendEvent(Unit)
			}
		}
	}

	fun detachFile() {
		file = null
		_fileAttachedEvent(file)
	}
}