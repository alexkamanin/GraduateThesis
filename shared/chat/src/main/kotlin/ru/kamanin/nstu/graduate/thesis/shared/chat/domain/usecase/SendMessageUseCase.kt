package ru.kamanin.nstu.graduate.thesis.shared.chat.domain.usecase

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import ru.kamanin.nstu.graduate.thesis.component.core.coroutines.dispatcher.ioDispatcher
import ru.kamanin.nstu.graduate.thesis.shared.chat.data.api.ChatApi
import ru.kamanin.nstu.graduate.thesis.shared.chat.domain.entity.MessageSummary
import javax.inject.Inject

class SendMessageUseCase @Inject constructor(
	@ioDispatcher private val ioDispatcher: CoroutineDispatcher,
	private val api: ChatApi
) {

	suspend operator fun invoke(periodId: Long, text: String) {
		withContext(ioDispatcher) {
			api.sendMessage(periodId, MessageSummary(text = text))
		}
	}
}