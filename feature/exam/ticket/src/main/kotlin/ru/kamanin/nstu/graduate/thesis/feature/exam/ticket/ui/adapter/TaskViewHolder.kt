package ru.kamanin.nstu.graduate.thesis.feature.exam.ticket.ui.adapter

import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import ru.kamanin.nstu.graduate.thesis.component.ui.core.colors.colorFromAttr
import ru.kamanin.nstu.graduate.thesis.component.ui.core.utils.inflate
import ru.kamanin.nstu.graduate.thesis.feature.exam.ticket.R
import ru.kamanin.nstu.graduate.thesis.feature.exam.ticket.databinding.ItemTaskBinding
import ru.kamanin.nstu.graduate.thesis.shared.ticket.domain.entity.Task
import ru.kamanin.nstu.graduate.thesis.shared.ticket.domain.entity.TaskState
import ru.kamanin.nstu.graduate.thesis.shared.ticket.domain.entity.TaskType

class TaskViewHolder(private val parent: ViewGroup) : RecyclerView.ViewHolder(parent.inflate(R.layout.item_task)) {

	private val viewBinding by viewBinding(ItemTaskBinding::bind)

	fun bind(task: Task, taskClicked: (Task) -> Unit) {
		val (icon, background) = when (task.state) {
			TaskState.NO_ANSWER   -> R.drawable.ic_no_answer to R.attr.colorBackgroundGreyTint
			TaskState.IN_PROGRESS -> R.drawable.ic_in_progress to R.attr.colorBackgroundGreyTint
			TaskState.NO_RATING   -> R.drawable.ic_no_rating to R.attr.colorBackgroundRedTint
			TaskState.CHECKING    -> R.drawable.ic_checking to R.attr.colorBlueVariant
			TaskState.SENT        -> R.drawable.ic_sent to R.attr.colorBackgroundYellowTint
			TaskState.RATED       -> R.drawable.ic_rated to R.attr.colorBackgroundGreenTint

		}
		val ratingText = if (task.rating == null) {
			parent.context.getString(R.string.item_unknown_rating_text, task.maxRating)
		} else {
			parent.context.getString(R.string.item_rating_text, task.rating, task.maxRating)
		}
		viewBinding.statusBackground.setCardBackgroundColor(parent.context.colorFromAttr(background))
		viewBinding.statusIcon.setImageDrawable(ContextCompat.getDrawable(parent.context, icon))
		viewBinding.taskName.text = when (task.taskType) {
			TaskType.QUESTION -> parent.resources.getString(
				R.string.question_title,
				task.number
			)
			TaskType.EXERCISE -> parent.resources.getString(
				R.string.exercise_title,
				task.number
			)
		}
		viewBinding.rating.text = ratingText
		viewBinding.root.setOnClickListener { taskClicked(task) }
	}
}