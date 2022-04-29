package ru.kamanin.nstu.graduate.thesis.shared.chat.ui.adapter

import androidx.recyclerview.widget.DiffUtil
import ru.kamanin.nstu.graduate.thesis.shared.chat.presentation.model.MessageItem

object MessageItemDiffCallback : DiffUtil.ItemCallback<MessageItem>() {

	override fun areItemsTheSame(
		oldItem: MessageItem,
		newItem: MessageItem
	): Boolean = oldItem.id == newItem.id

	override fun areContentsTheSame(
		oldItem: MessageItem,
		newItem: MessageItem
	): Boolean = oldItem == newItem
}