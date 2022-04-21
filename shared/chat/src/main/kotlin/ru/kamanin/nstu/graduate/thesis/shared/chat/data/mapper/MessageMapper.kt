package ru.kamanin.nstu.graduate.thesis.shared.chat.data.mapper

import ru.kamanin.nstu.graduate.thesis.component.core.time.timeStringFormat
import ru.kamanin.nstu.graduate.thesis.shared.chat.data.dto.MessageDto
import ru.kamanin.nstu.graduate.thesis.shared.chat.domain.entity.Message

fun MessageDto.toEntity() =
	Message(
		id = id,
		text = text,
		sendTime = sendTime.timeStringFormat,
		accountId = accountId,
		artefactId = artefactId
	)