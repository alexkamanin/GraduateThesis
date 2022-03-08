package ru.kamanin.nstu.graduate.thesis.feature.exam.chat.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import ru.kamanin.nstu.graduate.thesis.feature.exam.chat.R
import ru.kamanin.nstu.graduate.thesis.feature.exam.chat.databinding.ItemSentMessageBinding
import ru.kamanin.nstu.graduate.thesis.feature.exam.chat.domain.entity.Message

class SentMessageViewHolder(private val parent: ViewGroup) : RecyclerView.ViewHolder(getView(parent)) {

	private companion object {

		fun getView(parent: ViewGroup): View =
			LayoutInflater.from(parent.context)
				.inflate(R.layout.item_sent_message, parent, false)
	}

	private val viewBinding by viewBinding(ItemSentMessageBinding::bind)

	fun bind(message: Message.SentMessage) {
		with(viewBinding) {
			messageText.text = message.text
			messageTime.text = message.time
		}
	}
}