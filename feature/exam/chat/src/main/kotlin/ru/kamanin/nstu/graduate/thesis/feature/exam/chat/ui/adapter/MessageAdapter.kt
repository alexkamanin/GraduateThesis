package ru.kamanin.nstu.graduate.thesis.feature.exam.chat.ui.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.kamanin.nstu.graduate.thesis.component.core.recyclerview.DiffUtilCallback
import ru.kamanin.nstu.graduate.thesis.feature.exam.chat.R
import ru.kamanin.nstu.graduate.thesis.feature.exam.chat.domain.entity.Message

class MessageAdapter() : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

	var items: List<Message> = emptyList()
		set(value) {
			field = value
			diff.getDiffResult(value, detectMoves = false).dispatchUpdatesTo(this)
		}

	private val diff = DiffUtilCallback<Message> { old, new -> old.id == new.id }

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
		when (viewType) {
			R.layout.item_sent_message     -> SentMessageViewHolder(parent)
			R.layout.item_received_message -> ReceivedMessageViewHolder(parent)
			else                           -> throw Exception()
		}

	override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
		when (items[position]) {
			is Message.SentMessage     -> (holder as SentMessageViewHolder).bind(items[position] as Message.SentMessage)
			is Message.ReceivedMessage -> (holder as ReceivedMessageViewHolder).bind(items[position] as Message.ReceivedMessage)
		}
	}

	override fun getItemViewType(position: Int): Int =
		when (items[position]) {
			is Message.SentMessage     -> R.layout.item_sent_message
			is Message.ReceivedMessage -> R.layout.item_received_message
		}

	override fun getItemCount(): Int = items.size
}