package ru.kamanin.nstu.graduate.thesis.shared.artefact.data.mapper

import ru.kamanin.nstu.graduate.thesis.shared.artefact.data.dto.ArtefactDto
import ru.kamanin.nstu.graduate.thesis.shared.artefact.domain.entity.ArtefactMetaData

fun ArtefactDto.toEntity() =
	ArtefactMetaData(
		id = id,
		size = fileSize,
		extension = ArtefactMetaData.Extension.valueOf(artefactType),
		fullName = fileName
	)