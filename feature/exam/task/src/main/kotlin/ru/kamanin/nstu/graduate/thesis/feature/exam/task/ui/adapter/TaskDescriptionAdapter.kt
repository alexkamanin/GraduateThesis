package ru.kamanin.nstu.graduate.thesis.feature.exam.task.ui.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.kamanin.nstu.graduate.thesis.shared.ticket.domain.entity.Task

class TaskDescriptionAdapter(private val task: Task) : RecyclerView.Adapter<TaskDescriptionViewHolder>() {

	companion object {

		const val ITEM_COUNT = 1
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskDescriptionViewHolder =
		TaskDescriptionViewHolder(parent)

	override fun onBindViewHolder(holder: TaskDescriptionViewHolder, position: Int) {
		holder.bind(task)
	}

	override fun getItemCount(): Int = ITEM_COUNT
}