package ru.kamanin.nstu.graduate.thesis.feature.exam.chat.presentation

import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.kamanin.nstu.graduate.thesis.artefact.domain.usecase.DownloadArtefactUseCase
import ru.kamanin.nstu.graduate.thesis.artefact.domain.usecase.GetArtefactUseCase
import ru.kamanin.nstu.graduate.thesis.artefact.domain.usecase.UploadArtefactUseCase
import ru.kamanin.nstu.graduate.thesis.component.core.coroutines.exception.launch
import ru.kamanin.nstu.graduate.thesis.feature.exam.chat.domain.entity.Message
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
	private val uploadArtefactUseCase: UploadArtefactUseCase,
	private val downloadArtefactUseCase: DownloadArtefactUseCase,
	private var getArtefactUseCase: GetArtefactUseCase
) : ViewModel() {

	private companion object {

		const val EMPTY_TEXT = ""
	}

	private var messageList = mutableListOf<Message>()

	private val _state = MutableStateFlow<ChatState>(ChatState.Initial)
	val state: StateFlow<ChatState> get() = _state.asStateFlow()

	val message = MutableStateFlow(EMPTY_TEXT)

	private var i = 13L

	init {
		viewModelScope.launch {
			downloadArtefactUseCase(5142)

			getArtefactUseCase(5142)
		}
		messageList.addAll(
			listOf(
				Message.ReceivedMessage(5, "Поправил", "11:05"),
				Message.ReceivedMessage(6, "Ок", "11:05"),
				Message.SentMessage(7, "Проверьте", "11:03"),
				Message.SentMessage(8, "Зануление вынесено в отдельную функцию clearMas()", "11:03"),
				Message.ReceivedMessage(9, "Иванов, почему не зануляете ссылки в конце? Минус балл.", "11:02"),
				Message.SentMessage(10, "Хорошо, спасибо.", "10:41"),
				Message.ReceivedMessage(11, "Здравствуйте, да!", "10:32"),
				Message.SentMessage(
					12,
					"Евгений Леонидович, здравствуйте. Подскажите пожалуйста, во второй задаче необходимо учитывать пустой массив?",
					"10:30"
				)
			)
		)
		_state.value = ChatState.Content(messageList, false)
	}

	fun shareContent(uri: Uri) {
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

	fun send() {
		if (message.value.isNotEmpty()) {
			val new = listOf(Message.SentMessage(i++, message.value, "12:00")).plus(messageList)
			messageList = new.toMutableList()
			_state.value = ChatState.Content(new, true)
		}
	}
}