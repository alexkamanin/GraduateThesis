package ru.kamanin.nstu.graduate.thesis.feature.exam.task.ui.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import ru.kamanin.nstu.graduate.thesis.component.ui.core.utils.inflate
import ru.kamanin.nstu.graduate.thesis.feature.exam.task.R
import ru.kamanin.nstu.graduate.thesis.feature.exam.task.databinding.ItemTaskDescriptionBinding
import ru.kamanin.nstu.graduate.thesis.shared.ticket.domain.entity.Task

class TaskDescriptionViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(parent.inflate(R.layout.item_task_description)) {

	private val viewBinding by viewBinding(ItemTaskDescriptionBinding::bind)

	fun bind(task: Task) {
		viewBinding.taskText.text = task.description
		viewBinding.textTheme.text = task.theme
	}
}