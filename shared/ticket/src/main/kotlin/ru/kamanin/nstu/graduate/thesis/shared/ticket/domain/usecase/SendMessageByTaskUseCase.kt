package ru.kamanin.nstu.graduate.thesis.shared.ticket.domain.usecase

import ru.kamanin.nstu.graduate.thesis.shared.chat.domain.entity.Message
import ru.kamanin.nstu.graduate.thesis.shared.chat.domain.entity.MessageSummary
import ru.kamanin.nstu.graduate.thesis.shared.ticket.domain.repository.TaskRepository
import javax.inject.Inject

class SendMessageByTaskUseCase @Inject constructor(
	private val repository: TaskRepository
) {

	suspend operator fun invoke(answerId: Long, text: String? = null, artefactId: Long? = null): Message =
		repository.sendMessage(answerId, MessageSummary(text, artefactId))
}