package ru.kamanin.nstu.graduate.thesis.shared.exam.data.mapper

import ru.kamanin.nstu.graduate.thesis.shared.exam.data.dto.AnswerDto
import ru.kamanin.nstu.graduate.thesis.shared.exam.domain.entity.Answer

fun AnswerDto.toEntity() =
	Answer(
		id = id,
		cost = this.task.cost,
		description = this.task.text,
		theme = this.task.theme.name,
		taskType = this.task.taskType
	)