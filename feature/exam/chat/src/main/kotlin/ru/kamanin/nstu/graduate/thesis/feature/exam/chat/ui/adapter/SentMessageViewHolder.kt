package ru.kamanin.nstu.graduate.thesis.feature.exam.chat.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import ru.kamanin.nstu.graduate.thesis.component.ui.core.icons.icon
import ru.kamanin.nstu.graduate.thesis.feature.exam.chat.R
import ru.kamanin.nstu.graduate.thesis.feature.exam.chat.databinding.ItemSentMessageBinding
import ru.kamanin.nstu.graduate.thesis.feature.exam.chat.presentation.model.MessageItem
import ru.kamanin.nstu.graduate.thesis.shared.artefact.domain.entity.Artefact

class SentMessageViewHolder(private val parent: ViewGroup) : RecyclerView.ViewHolder(getView(parent)) {

	private companion object {

		fun getView(parent: ViewGroup): View =
			LayoutInflater.from(parent.context)
				.inflate(R.layout.item_sent_message, parent, false)
	}

	private val viewBinding by viewBinding(ItemSentMessageBinding::bind)

	fun bind(message: MessageItem.SentMessage, artefactClicked: (Artefact) -> Unit) {
		with(viewBinding) {
			messageTime.text = message.time

			if (message.artefact != null) {
				artefactType.text = message.artefact.toSingleLineType()
				artefactName.text = message.artefact.fullName
				artefactIcon.setImageDrawable(ContextCompat.getDrawable(parent.context, message.artefact.icon))

				artefactName.isVisible = true
				artefactIcon.isVisible = true
				artefactType.isVisible = true
				artefactContainer.isVisible = true

				artefactContainer.setOnClickListener { artefactClicked(message.artefact) }
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

	private fun Artefact.toSingleLineType(): String =
		when {
			sizeInMegaByte > 0 -> parent.context.getString(R.string.hint_artefact_type_mb, sizeInMegaByte, extension)
			else               -> parent.context.getString(R.string.hint_artefact_type_kb, sizeInKiloByte, extension)
		}
}