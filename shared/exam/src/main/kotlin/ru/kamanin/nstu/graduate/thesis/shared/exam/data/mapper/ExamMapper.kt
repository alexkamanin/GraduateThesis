package ru.kamanin.nstu.graduate.thesis.shared.exam.data.mapper

import ru.kamanin.nstu.graduate.thesis.shared.account.data.mapper.toEntity
import ru.kamanin.nstu.graduate.thesis.shared.exam.data.dto.ExamDto
import ru.kamanin.nstu.graduate.thesis.shared.exam.domain.entity.Exam

fun ExamDto.toEntity() =
	Exam(
		id = id,
		name = disciplineName,
		mark = semesterRating + examRating,
		period = Exam.Period(id = examPeriod.id, start = examPeriod.start, end = examPeriod.end, state = examPeriod.state),
		allowed = allowed,
		teacher = teacher.toEntity(),
		regulationRating = Exam.RegulationRating(
			maxQuestionRating = maxQuestionRating,
			maxExerciseRating = maxExerciseRating
		)
	)

fun ExamDto.Teacher.toEntity() =
	Exam.Teacher(
		id = id,
		account = account.toEntity()
	)