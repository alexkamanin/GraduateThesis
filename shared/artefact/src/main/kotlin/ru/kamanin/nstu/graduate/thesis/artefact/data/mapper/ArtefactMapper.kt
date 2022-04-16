package ru.kamanin.nstu.graduate.thesis.artefact.data.mapper

import ru.kamanin.nstu.graduate.thesis.artefact.data.dto.ArtefactDto
import ru.kamanin.nstu.graduate.thesis.artefact.domain.entity.Artefact

fun ArtefactDto.toEntity() =
	Artefact(
		id = id,
		size = fileSize,
		extension = artefactType,
		fullName = fileName
	)