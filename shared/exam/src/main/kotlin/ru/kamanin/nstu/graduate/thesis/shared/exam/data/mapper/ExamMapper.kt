package ru.kamanin.nstu.graduate.thesis.shared.exam.data.mapper

import ru.kamanin.nstu.graduate.thesis.shared.exam.data.dto.ExamDto
import ru.kamanin.nstu.graduate.thesis.shared.exam.domain.entity.Exam

fun ExamDto.toEntity() =
	Exam(
		id = id,
		name = examPeriod.exam.examRule.discipline.name,
		mark = semesterRating + examRating,
		period = Exam.Period(id = id, start = examPeriod.start, end = examPeriod.end),
		allowed = allowed,
		teacher = examPeriod.exam.teacher.toEntity()
	)

fun ExamDto.ExamPeriod.Exam.Teacher.toEntity() =
	Exam.Teacher(
		id = id,
		username = account.username,
		name = account.name,
		surname = account.surname
	)