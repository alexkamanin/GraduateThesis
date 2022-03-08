package ru.kamanin.nstu.graduate.thesis.shared.exam.data.mapper

import ru.kamanin.nstu.graduate.thesis.shared.exam.data.dto.ExamDto
import ru.kamanin.nstu.graduate.thesis.shared.exam.domain.entity.Exam

fun ExamDto.toEntity() =
	Exam(
		id = id,
		name = examPeriod.exam.examRule.discipline.name,
		mark = semesterRating,
		dateTime = examPeriod.start,
		allowed = allowed
	)