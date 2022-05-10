package ru.kamanin.nstu.graduate.thesis.feature.exam.chat.ui.adapter

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.kamanin.nstu.graduate.thesis.feature.exam.chat.R
import ru.kamanin.nstu.graduate.thesis.shared.artefact.domain.entity.ArtefactMetaData
import ru.kamanin.nstu.graduate.thesis.shared.chat.presentation.model.MessageItem
import ru.kamanin.nstu.graduate.thesis.shared.chat.ui.adapter.MessageItemDiffCallback

class MessageAdapter(
	private val artefactClicked: (ArtefactMetaData) -> Unit,
	private val textClicked: (String) -> Unit
) : PagingDataAdapter<MessageItem, RecyclerView.ViewHolder>(MessageItemDiffCallback) {

	override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
		when (val item = getItem(position)) {
			is MessageItem.SentMessage     -> (holder as SentMessageViewHolder).bind(item, artefactClicked, textClicked)
			is MessageItem.ReceivedMessage -> (holder as ReceivedMessageViewHolder).bind(item, artefactClicked, textClicked)

			else                           -> throw IllegalStateException("Unknown type '$item' in message adapter")
		}
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
		when (viewType) {
			R.layout.item_sent_message     -> SentMessageViewHolder(parent)
			R.layout.item_received_message -> ReceivedMessageViewHolder(parent)
			else                           -> throw IllegalStateException("Unknown viewType '$viewType' in message adapter")
		}

	override fun getItemViewType(position: Int): Int =
		when (val item = getItem(position)) {
			is MessageItem.SentMessage     -> R.layout.item_sent_message
			is MessageItem.ReceivedMessage -> R.layout.item_received_message

			else                           -> throw IllegalStateException("Unknown type '$item' in message adapter")
		}
}