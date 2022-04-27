package ru.kamanin.nstu.graduate.thesis.shared.ticket.data.mapper

import ru.kamanin.nstu.graduate.thesis.shared.exam.domain.entity.Exam
import ru.kamanin.nstu.graduate.thesis.shared.ticket.data.dto.TaskDto
import ru.kamanin.nstu.graduate.thesis.shared.ticket.domain.entity.Task
import ru.kamanin.nstu.graduate.thesis.shared.ticket.domain.entity.TaskType

object TaskMapper {

	fun convert(taskDto: TaskDto, regulationRating: Exam.RegulationRating): Task =
		with(taskDto) {
			val maxRating = when (task.taskType) {
				TaskType.QUESTION -> regulationRating.maxQuestionRating
				TaskType.EXERCISE -> regulationRating.maxExerciseRating
			}

			Task(
				id = id,
				number = number,
				rating = rating,
				maxRating = maxRating,
				description = task.text,
				theme = task.themeName,
				taskType = task.taskType,
				status = status
			)
		}
}