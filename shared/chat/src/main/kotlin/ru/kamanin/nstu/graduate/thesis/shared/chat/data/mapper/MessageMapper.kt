package ru.kamanin.nstu.graduate.thesis.shared.chat.data.mapper

import ru.kamanin.nstu.graduate.thesis.shared.chat.data.dto.MessageDto
import ru.kamanin.nstu.graduate.thesis.shared.chat.domain.entity.Message
import ru.kamanin.nstu.graduate.thesis.utils.time.timeStringFormat

fun MessageDto.toEntity() =
	Message(
		id = id,
		text = text,
		sendTime = sendTime.timeStringFormat,
		accountId = accountId,
		artefactId = artefactId
	)