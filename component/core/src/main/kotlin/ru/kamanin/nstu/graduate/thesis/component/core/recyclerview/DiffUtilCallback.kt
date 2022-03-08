package ru.kamanin.nstu.graduate.thesis.component.core.recyclerview

import androidx.recyclerview.widget.DiffUtil

class DiffUtilCallback<T>(private val compare: (T, T) -> Boolean) : DiffUtil.Callback() {

	private var oldList: List<T> = emptyList()
	private var newList: List<T> = emptyList()

	fun getDiffResult(newList: List<T>, detectMoves: Boolean = true): DiffUtil.DiffResult {
		oldList = this.newList
		this.newList = newList
		return DiffUtil.calculateDiff(this, detectMoves)
	}

	override fun getOldListSize(): Int = oldList.size

	override fun getNewListSize(): Int = newList.size

	override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
		compare(oldList[oldItemPosition], newList[newItemPosition])

	override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
		oldList[oldItemPosition] == newList[newItemPosition]
}