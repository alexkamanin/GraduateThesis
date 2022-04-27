package ru.kamanin.nstu.graduate.thesis.feature.exam.ticket.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import ru.kamanin.nstu.graduate.thesis.component.ui.core.colors.colorFromAttr
import ru.kamanin.nstu.graduate.thesis.feature.exam.ticket.R
import ru.kamanin.nstu.graduate.thesis.feature.exam.ticket.databinding.ItemTaskBinding
import ru.kamanin.nstu.graduate.thesis.shared.ticket.domain.entity.Status
import ru.kamanin.nstu.graduate.thesis.shared.ticket.domain.entity.Task

class TaskViewHolder(private val parent: ViewGroup) : RecyclerView.ViewHolder(getView(parent)) {

	private companion object {

		fun getView(parent: ViewGroup): View =
			LayoutInflater.from(parent.context)
				.inflate(R.layout.item_task, parent, false)
	}

	private val viewBinding by viewBinding(ItemTaskBinding::bind)

	fun bind(task: Task, taskClicked: (Task) -> Unit) {
		val (icon, background) = when (task.status) {
			Status.CHECKING -> R.drawable.ic_checking to R.attr.colorBackgroundGreyTint
			Status.REJECTED -> R.drawable.ic_rejected to R.attr.colorBackgroundRedTint
			Status.REVISION -> R.drawable.ic_revision to R.attr.colorBackgroundYellowTint
			Status.APPROVED -> R.drawable.ic_approved to R.attr.colorBackgroundGreenTint
			null            -> R.drawable.ic_new to R.attr.colorBackgroundGreyTint
		}
		val ratingText = if (task.rating == null) {
			parent.context.getString(R.string.item_unknown_rating_text, task.maxRating)
		} else {
			parent.context.getString(R.string.item_rating_text, task.rating, task.maxRating)
		}
		viewBinding.statusBackground.setCardBackgroundColor(parent.context.colorFromAttr(background))
		viewBinding.statusIcon.setImageDrawable(ContextCompat.getDrawable(parent.context, icon))
		viewBinding.taskName.text = when (task.taskType) {
			ru.kamanin.nstu.graduate.thesis.shared.ticket.domain.entity.TaskType.QUESTION -> parent.resources.getString(
				R.string.question_title,
				task.number
			)
			ru.kamanin.nstu.graduate.thesis.shared.ticket.domain.entity.TaskType.EXERCISE -> parent.resources.getString(
				R.string.exercise_title,
				task.number
			)
		}
		viewBinding.rating.text = ratingText
		viewBinding.root.setOnClickListener { taskClicked(task) }
	}
}