package ru.kamanin.nstu.graduate.thesis.feature.exam.task.ui.adapter

import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import ru.kamanin.nstu.graduate.thesis.component.ui.core.icons.icon
import ru.kamanin.nstu.graduate.thesis.component.ui.core.strings.artefactSingleLineType
import ru.kamanin.nstu.graduate.thesis.component.ui.core.utils.inflate
import ru.kamanin.nstu.graduate.thesis.feature.exam.task.R
import ru.kamanin.nstu.graduate.thesis.feature.exam.task.databinding.ItemTaskMessageBinding
import ru.kamanin.nstu.graduate.thesis.shared.artefact.domain.entity.ArtefactMetaData
import ru.kamanin.nstu.graduate.thesis.shared.chat.presentation.model.MessageItem

class TaskMessageViewHolder(private val parent: ViewGroup) : RecyclerView.ViewHolder(parent.inflate(R.layout.item_task_message)) {

	private val viewBinding by viewBinding(ItemTaskMessageBinding::bind)

	fun bind(
		message: MessageItem,
		artefactClicked: (ArtefactMetaData) -> Unit,
		textClicked: (String) -> Unit
	) {
		with(viewBinding) {
			messageAuthor.text = "${message.account.surname} ${message.account.name}"
			messageTime.text = message.time

			if (message.artefact != null) {
				val artefact = requireNotNull(message.artefact)

				artefactType.text = artefactSingleLineType(parent.context, artefact)
				artefactName.text = artefact.fullName
				artefactIcon.setImageDrawable(ContextCompat.getDrawable(parent.context, message.artefact!!.icon))

				if (message.text != null) {
					messageText.text = message.text
					viewBinding.messageText.setOnLongClickListener {
						textClicked(viewBinding.messageText.text.toString())
						true
					}
					messageText.isVisible = true
				}

				artefactName.isVisible = true
				artefactIcon.isVisible = true
				artefactType.isVisible = true
				artefactContainer.isVisible = true

				artefactContainer.setOnClickListener { artefactClicked(artefact) }
			} else {
				messageText.text = message.text
				viewBinding.messageText.setOnLongClickListener {
					textClicked(viewBinding.messageText.text.toString())
					true
				}

				messageText.isVisible = true
				artefactName.isVisible = false
				artefactIcon.isVisible = false
				artefactType.isVisible = false
				artefactContainer.isVisible = false
			}
		}
	}
}