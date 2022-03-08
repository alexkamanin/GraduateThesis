package ru.kamanin.nstu.graduate.thesis.shared.session.data.mapper

import ru.kamanin.nstu.graduate.thesis.shared.session.data.dto.SessionDto
import ru.kamanin.nstu.graduate.thesis.shared.session.domain.entity.Session

fun SessionDto.toEntity() =
	Session(
		token = token,
		roles = roles
	)