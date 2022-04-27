package ru.kamanin.nstu.graduate.thesis.feature.exam.list.ui.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.kamanin.nstu.graduate.thesis.component.ui.core.utils.DiffUtilCallback
import ru.kamanin.nstu.graduate.thesis.shared.exam.domain.entity.Exam

class ExamAdapter(private val onExamClick: (Exam) -> Unit) : RecyclerView.Adapter<ExamViewHolder>() {

	var items: List<Exam> = emptyList()
		set(value) {
			field = value
			notifyDataSetChanged()
		}

	private val diff = DiffUtilCallback<Exam> { old, new -> old.id == new.id }

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExamViewHolder =
		ExamViewHolder(parent)

	override fun onBindViewHolder(holder: ExamViewHolder, position: Int) {
		holder.bind(items[position], onExamClick)
	}

	override fun getItemCount(): Int = items.size
}