package ru.kamanin.nstu.graduate.thesis.feature.exam.chat.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import ru.kamanin.nstu.graduate.thesis.feature.exam.chat.R
import ru.kamanin.nstu.graduate.thesis.feature.exam.chat.databinding.ItemReceivedMessageBinding
import ru.kamanin.nstu.graduate.thesis.feature.exam.chat.domain.entity.Message

class ReceivedMessageViewHolder(private val parent: ViewGroup) : RecyclerView.ViewHolder(getView(parent)) {

	private companion object {

		fun getView(parent: ViewGroup): View =
			LayoutInflater.from(parent.context)
				.inflate(R.layout.item_received_message, parent, false)
	}

	private val viewBinding by viewBinding(ItemReceivedMessageBinding::bind)

	fun bind(message: Message.ReceivedMessage) {
		with(viewBinding) {
			messageText.text = message.text
			messageTime.text = message.time
		}
	}
}