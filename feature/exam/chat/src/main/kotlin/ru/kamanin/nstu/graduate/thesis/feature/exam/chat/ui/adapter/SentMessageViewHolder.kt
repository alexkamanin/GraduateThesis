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

	private val viewBinding by viewBinding(ItemSentMessageBinding::bind)

	fun bind(message: MessageItem.SentMessage, artefactClicked: (ArtefactMetaData) -> Unit) {
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

				artefactContainer.setOnClickListener { artefactClicked(artefact) }
			} else {
				artefactType.isVisible = false
				artefactIcon.isVisible = false
				artefactType.isVisible = false
				artefactContainer.isVisible = false
			}

			if (message.text != null) {
				messageText.text = message.text
				messageText.isVisible = true
			} else {
				messageText.isVisible = false
			}
		}
	}
}