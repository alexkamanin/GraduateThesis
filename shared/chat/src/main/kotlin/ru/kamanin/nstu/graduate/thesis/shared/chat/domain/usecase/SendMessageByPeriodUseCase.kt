package ru.kamanin.nstu.graduate.thesis.shared.chat.domain.usecase

import ru.kamanin.nstu.graduate.thesis.shared.chat.domain.entity.Message
import ru.kamanin.nstu.graduate.thesis.shared.chat.domain.entity.MessageSummary
import ru.kamanin.nstu.graduate.thesis.shared.chat.domain.repository.ChatRepository
import javax.inject.Inject

class SendMessageByPeriodUseCase @Inject constructor(
	private val repository: ChatRepository
) {

	suspend operator fun invoke(periodId: Long, text: String? = null, artefactId: Long? = null): Message =
		repository.sendMessage(periodId, MessageSummary(text, artefactId))
}