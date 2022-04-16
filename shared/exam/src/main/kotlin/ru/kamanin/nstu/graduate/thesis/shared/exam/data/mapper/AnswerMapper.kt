package ru.kamanin.nstu.graduate.thesis.shared.exam.data.mapper

import ru.kamanin.nstu.graduate.thesis.shared.exam.data.dto.AnswerDto
import ru.kamanin.nstu.graduate.thesis.shared.exam.domain.entity.Answer
import ru.kamanin.nstu.graduate.thesis.shared.exam.domain.entity.Exam
import ru.kamanin.nstu.graduate.thesis.shared.exam.domain.entity.TaskType

object AnswerMapper {

	fun convert(answerDto: AnswerDto, regulationRating: Exam.RegulationRating): Answer =
		with(answerDto) {
			val maxRating = when (task.taskType) {
				TaskType.QUESTION -> regulationRating.maxQuestionRating
				TaskType.EXERCISE -> regulationRating.maxExerciseRating
			}

			Answer(
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