package ru.kamanin.nstu.graduate.thesis.shared.exam.data.mapper

import ru.kamanin.nstu.graduate.thesis.shared.account.data.mapper.toEntity
import ru.kamanin.nstu.graduate.thesis.shared.exam.data.dto.ExamDto
import ru.kamanin.nstu.graduate.thesis.shared.exam.domain.entity.Exam

fun ExamDto.toEntity() =
	Exam(
		id = id,
		name = exam.name,
		mark = semesterRating + questionRating + exerciseRating,
		period = Exam.Period(start = exam.start, end = exam.end),
		teacher = teacher.toEntity(),
		regulationRating = Exam.RegulationRating(
			maxQuestionRating = examRule.singleQuestionDefaultRating,
			maxExerciseRating = examRule.singleExerciseDefaultRating
		),
		examState = exam.state,
		ratingState = studentRatingState
	)

fun ExamDto.Teacher.toEntity() =
	Exam.Teacher(
		id = id,
		account = account.toEntity()
	)