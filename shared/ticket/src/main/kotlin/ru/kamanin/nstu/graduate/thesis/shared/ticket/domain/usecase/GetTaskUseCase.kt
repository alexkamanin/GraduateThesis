package ru.kamanin.nstu.graduate.thesis.shared.ticket.domain.usecase

import ru.kamanin.nstu.graduate.thesis.shared.ticket.data.dto.TaskSummary
import ru.kamanin.nstu.graduate.thesis.shared.ticket.domain.repository.TaskRepository
import javax.inject.Inject

class GetTaskUseCase @Inject constructor(
	private val repository: TaskRepository
) {

	suspend operator fun invoke(taskId: Long): TaskSummary =
		repository.getTask(taskId)
}