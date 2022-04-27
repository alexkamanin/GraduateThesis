package ru.kamanin.nstu.graduate.thesis.shared.artefact.data.mapper

import ru.kamanin.nstu.graduate.thesis.shared.artefact.data.dto.ArtefactDto
import ru.kamanin.nstu.graduate.thesis.shared.artefact.domain.entity.Artefact

fun ArtefactDto.toEntity() =
	Artefact(
		id = id,
		size = fileSize,
		extension = Artefact.Extension.valueOf(artefactType),
		fullName = fileName
	)