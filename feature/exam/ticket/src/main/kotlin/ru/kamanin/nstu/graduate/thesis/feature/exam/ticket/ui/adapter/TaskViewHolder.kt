package ru.kamanin.nstu.graduate.thesis.feature.exam.ticket.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import ru.kamanin.nstu.graduate.thesis.component.ui.colors.colorFromAttr
import ru.kamanin.nstu.graduate.thesis.feature.exam.ticket.R
import ru.kamanin.nstu.graduate.thesis.feature.exam.ticket.databinding.ItemTaskBinding
import ru.kamanin.nstu.graduate.thesis.feature.exam.ticket.ui.model.TaskItem
import ru.kamanin.nstu.graduate.thesis.shared.exam.domain.entity.TaskType

class TaskViewHolder(private val parent: ViewGroup) : RecyclerView.ViewHolder(getView(parent)) {

	private companion object {

		fun getView(parent: ViewGroup): View =
			LayoutInflater.from(parent.context)
				.inflate(R.layout.item_task, parent, false)
	}

	private val viewBinding by viewBinding(ItemTaskBinding::bind)

	fun bind(taskItem: TaskItem, taskClicked: (TaskItem) -> Unit) {
		val (icon, background) = when (taskItem.status) {
			TaskItem.Status.CHECKING -> R.drawable.ic_checking to R.attr.colorBackgroundGreyTint
			TaskItem.Status.REJECTED -> R.drawable.ic_rejected to R.attr.colorBackgroundRedTint
			TaskItem.Status.REVISION -> R.drawable.ic_revision to R.attr.colorBackgroundYellowTint
			TaskItem.Status.APPROVED -> R.drawable.ic_approved to R.attr.colorBackgroundGreenTint
		}
		viewBinding.statusBackground.setCardBackgroundColor(parent.context.colorFromAttr(background))
		viewBinding.statusIcon.setImageDrawable(ContextCompat.getDrawable(parent.context, icon))
		viewBinding.taskName.text = when (taskItem.taskType) {
			TaskType.QUESTION -> parent.resources.getString(R.string.question_title, taskItem.number)
			TaskType.EXERCISE -> parent.resources.getString(R.string.exercise_title, taskItem.number)
		}
		viewBinding.root.setOnClickListener { taskClicked(taskItem) }
	}
}