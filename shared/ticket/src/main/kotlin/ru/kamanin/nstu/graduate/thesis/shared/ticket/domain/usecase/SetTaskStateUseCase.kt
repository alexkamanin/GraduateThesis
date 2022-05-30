package ru.kamanin.nstu.graduate.thesis.shared.ticket.domain.usecase

import ru.kamanin.nstu.graduate.thesis.shared.ticket.domain.entity.TaskState
import ru.kamanin.nstu.graduate.thesis.shared.ticket.domain.repository.TaskRepository
import javax.inject.Inject

class SetTaskStateUseCase @Inject constructor(
	private val repository: TaskRepository
) {

	suspend operator fun invoke(id: Long, state: TaskState) {
		repository.setState(id, state)
	}
}