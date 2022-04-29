package ru.kamanin.nstu.graduate.thesis.feature.exam.chat.ui.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.kamanin.nstu.graduate.thesis.component.ui.core.utils.DiffUtilCallback
import ru.kamanin.nstu.graduate.thesis.feature.exam.chat.R
import ru.kamanin.nstu.graduate.thesis.shared.artefact.domain.entity.ArtefactMetaData
import ru.kamanin.nstu.graduate.thesis.shared.chat.presentation.model.MessageItem

@Deprecated(message = "Оставлено для худших времен")
class DeprecatedMessageAdapter(private val artefactClicked: (ArtefactMetaData) -> Unit) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

	var items: List<MessageItem> = emptyList()
		set(value) {
			field = value
			diff.getDiffResult(value, detectMoves = false).dispatchUpdatesTo(this)
		}

	private val diff = DiffUtilCallback<MessageItem> { old, new -> old.id == new.id }

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
		when (viewType) {
			R.layout.item_sent_message     -> SentMessageViewHolder(parent)
			R.layout.item_received_message -> ReceivedMessageViewHolder(parent)
			else                           -> throw Exception()
		}

	override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
		when (items[position]) {
			is MessageItem.SentMessage     -> (holder as SentMessageViewHolder).bind(items[position] as MessageItem.SentMessage, artefactClicked)
			is MessageItem.ReceivedMessage -> (holder as ReceivedMessageViewHolder).bind(items[position] as MessageItem.ReceivedMessage, artefactClicked)
		}
	}

	override fun getItemViewType(position: Int): Int =
		when (items[position]) {
			is MessageItem.SentMessage     -> R.layout.item_sent_message
			is MessageItem.ReceivedMessage -> R.layout.item_received_message
		}

	override fun getItemCount(): Int = items.size
}