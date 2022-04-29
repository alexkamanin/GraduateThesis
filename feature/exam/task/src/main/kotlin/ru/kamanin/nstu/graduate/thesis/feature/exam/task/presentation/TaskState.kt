package ru.kamanin.nstu.graduate.thesis.feature.exam.task.presentation

import androidx.paging.PagingData
import ru.kamanin.nstu.graduate.thesis.shared.chat.presentation.model.MessageItem
import ru.kamanin.nstu.graduate.thesis.shared.ticket.domain.entity.Task

data class TaskState(
	val task: Task,
	val messages: PagingData<MessageItem>
)