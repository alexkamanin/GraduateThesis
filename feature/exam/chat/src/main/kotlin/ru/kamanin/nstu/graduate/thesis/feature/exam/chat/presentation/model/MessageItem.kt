package ru.kamanin.nstu.graduate.thesis.feature.exam.chat.presentation.model

import ru.kamanin.nstu.graduate.thesis.artefact.domain.entity.Artefact
import ru.kamanin.nstu.graduate.thesis.shared.account.domain.entity.Account
import ru.kamanin.nstu.graduate.thesis.shared.chat.domain.entity.Message

sealed interface MessageItem {

	companion object {

		suspend fun from(
			message: Message,
			personalAccount: Account,
			otherAccount: Account,
			loadArtefact: suspend (artefactId: Long) -> Artefact
		): MessageItem = with(message) {

			if (personalAccount.id == message.accountId) {
				SentMessage(
					id = id,
					text = text,
					time = sendTime,
					account = personalAccount,
					artefact = artefactId?.let { loadArtefact(it) }
				)
			} else {
				ReceivedMessage(
					id = id,
					text = text,
					time = sendTime,
					account = otherAccount,
					artefact = artefactId?.let { loadArtefact(it) }
				)
			}
		}
	}

	val id: Long
	val time: String
	val account: Account
	val text: String?
	val artefact: Artefact?

	data class SentMessage(
		override val id: Long,
		override val time: String,
		override val account: Account,
		override val text: String? = null,
		override val artefact: Artefact? = null
	) : MessageItem

	data class ReceivedMessage(
		override val id: Long,
		override val time: String,
		override val account: Account,
		override val text: String? = null,
		override val artefact: Artefact? = null
	) : MessageItem
}