package ru.kamanin.nstu.graduate.thesis.feature.exam.task.ui.adapter

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import ru.kamanin.nstu.graduate.thesis.shared.artefact.domain.entity.ArtefactMetaData
import ru.kamanin.nstu.graduate.thesis.shared.chat.presentation.model.MessageItem
import ru.kamanin.nstu.graduate.thesis.shared.chat.ui.adapter.MessageItemDiffCallback

class TaskMessageAdapter(
	private val artefactClicked: (ArtefactMetaData) -> Unit,
	private val textClicked: (String) -> Unit
) : PagingDataAdapter<MessageItem, TaskMessageViewHolder>(MessageItemDiffCallback) {

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskMessageViewHolder =
		TaskMessageViewHolder(parent)

	override fun onBindViewHolder(holder: TaskMessageViewHolder, position: Int) {
		holder.bind(requireNotNull(getItem(position)), artefactClicked, textClicked)
	}
}