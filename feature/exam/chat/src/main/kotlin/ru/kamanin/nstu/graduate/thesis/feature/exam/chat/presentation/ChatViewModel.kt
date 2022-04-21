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
import ru.kamanin.nstu.graduate.thesis.artefact.domain.entity.Artefact
import ru.kamanin.nstu.graduate.thesis.artefact.domain.usecase.DownloadArtefactUseCase
import ru.kamanin.nstu.graduate.thesis.artefact.domain.usecase.GetArtefactUseCase
import ru.kamanin.nstu.graduate.thesis.artefact.domain.usecase.UploadArtefactUseCase
import ru.kamanin.nstu.graduate.thesis.component.core.coroutines.exception.launch
import ru.kamanin.nstu.graduate.thesis.component.core.coroutines.flow.LiveState
import ru.kamanin.nstu.graduate.thesis.component.core.coroutines.flow.MutableLiveState
import ru.kamanin.nstu.graduate.thesis.component.core.coroutines.flow.asLiveState
import ru.kamanin.nstu.graduate.thesis.component.core.coroutines.flow.invoke
import ru.kamanin.nstu.graduate.thesis.feature.exam.chat.presentation.model.MessageItem
import ru.kamanin.nstu.graduate.thesis.shared.account.domain.usecase.GetPersonalAccountUseCase
import ru.kamanin.nstu.graduate.thesis.shared.chat.data.paging.mapPaging
import ru.kamanin.nstu.graduate.thesis.shared.chat.domain.usecase.GetMessagesUseCase
import ru.kamanin.nstu.graduate.thesis.shared.chat.domain.usecase.SendMessageUseCase
import ru.kamanin.nstu.graduate.thesis.shared.exam.domain.entity.Exam
import javax.inject.Inject

//TODO при скачивании файла кидать пользователю пуш, при клике открывать файл

@HiltViewModel
class ChatViewModel @Inject constructor(
	private val uploadArtefactUseCase: UploadArtefactUseCase,
	private val downloadArtefactUseCase: DownloadArtefactUseCase,
	private val getArtefactUseCase: GetArtefactUseCase,
	private val getPersonalAccountUseCase: GetPersonalAccountUseCase,
	private val getMessagesUseCase: GetMessagesUseCase,
	private val sendMessageUseCase: SendMessageUseCase,
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

	val message = MutableStateFlow(EMPTY_TEXT)

	init {
		_state.value = ChatState.Loading

		viewModelScope.launch {

			val personalAccount = getPersonalAccountUseCase()
			val teacherAccount = exam.teacher.account

			getMessagesUseCase(5165)
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

	fun shareContent(uri: Uri) {
		Log.d("TEST_TECH", uri.toString())
		viewModelScope.launch(::handleArtefactLoadError) {
			val artefact = uploadArtefactUseCase(uri)
		}
	}

	private fun handleArtefactLoadError(throwable: Throwable) {
		Log.e("TEST_TECH", throwable.stackTraceToString())
	}

	fun shareImage(bitmap: Bitmap) {
		Log.d("TEST_TECH", bitmap.toString())
	}

	fun openArtefact(artefact: Artefact) {
		Log.d("TEST_TECH", artefact.toString())

//		viewModelScope.launch {
//			downloadArtefactUseCase(5142)
//			getArtefactUseCase(5142)
//		}
	}

	fun send() {
		if (message.value.isNotEmpty()) {
			viewModelScope.launch {
				sendMessageUseCase(5165, message.value)
				_sendEvent(Unit)
			}
		}
	}
}