package ru.kamanin.nstu.graduate.thesis.feature.exam.chat.ui.adapter

import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import ru.kamanin.nstu.graduate.thesis.component.ui.core.icons.icon
import ru.kamanin.nstu.graduate.thesis.component.ui.core.strings.artefactSingleLineType
import ru.kamanin.nstu.graduate.thesis.component.ui.core.utils.inflate
import ru.kamanin.nstu.graduate.thesis.feature.exam.chat.R
import ru.kamanin.nstu.graduate.thesis.feature.exam.chat.databinding.ItemSentMessageBinding
import ru.kamanin.nstu.graduate.thesis.shared.artefact.domain.entity.ArtefactMetaData
import ru.kamanin.nstu.graduate.thesis.shared.chat.presentation.model.MessageItem

class SentMessageViewHolder(private val parent: ViewGroup) : RecyclerView.ViewHolder(parent.inflate(R.layout.item_sent_message)) {

	private companion object {

		const val EMPTY_PADDING = 0
		const val START_PADDING = 5
		const val END_PADDING = 60
	}

	private val viewBinding by viewBinding(ItemSentMessageBinding::bind)

	fun bind(
		message: MessageItem.SentMessage,
		artefactClicked: (ArtefactMetaData) -> Unit,
		textClicked: (String) -> Unit
	) {
		with(viewBinding) {
			messageTime.text = message.time

			if (message.artefact != null) {
				val artefact = requireNotNull(message.artefact)

				artefactType.text = artefactSingleLineType(parent.context, artefact)
				artefactName.text = artefact.fullName
				artefactIcon.setImageDrawable(ContextCompat.getDrawable(parent.context, artefact.icon))

				artefactName.isVisible = true
				artefactIcon.isVisible = true
				artefactType.isVisible = true
				artefactContainer.isVisible = true
				messageText.setPadding(EMPTY_PADDING, EMPTY_PADDING, EMPTY_PADDING, EMPTY_PADDING)

				artefactContainer.setOnClickListener { artefactClicked(artefact) }
			} else {
				artefactType.isVisible = false
				artefactIcon.isVisible = false
				artefactType.isVisible = false
				artefactContainer.isVisible = false
				messageText.setPadding(START_PADDING, EMPTY_PADDING, END_PADDING, EMPTY_PADDING)
			}

			if (message.text != null) {
				messageText.text = message.text
				messageText.isVisible = true
				messageText.setOnLongClickListener {
					textClicked(messageText.text.toString())
					true
				}
			} else {
				messageText.isVisible = false
			}
		}
	}
}