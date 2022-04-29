package ru.kamanin.nstu.graduate.thesis.shared.chat.presentation.model

import ru.kamanin.nstu.graduate.thesis.shared.account.domain.entity.Account
import ru.kamanin.nstu.graduate.thesis.shared.artefact.domain.entity.ArtefactMetaData
import ru.kamanin.nstu.graduate.thesis.shared.chat.domain.entity.Message

sealed interface MessageItem {

	companion object {

		suspend fun from(
			message: Message,
			personalAccount: Account,
			otherAccount: Account,
			loadArtefact: suspend (artefactId: Long) -> ArtefactMetaData
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
	val artefact: ArtefactMetaData?

	data class SentMessage(
		override val id: Long,
		override val time: String,
		override val account: Account,
		override val text: String? = null,
		override val artefact: ArtefactMetaData? = null
	) : MessageItem

	data class ReceivedMessage(
		override val id: Long,
		override val time: String,
		override val account: Account,
		override val text: String? = null,
		override val artefact: ArtefactMetaData? = null
	) : MessageItem
}