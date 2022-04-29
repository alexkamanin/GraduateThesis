package ru.kamanin.nstu.graduate.thesis.shared.artefact.domain.usecase

import ru.kamanin.nstu.graduate.thesis.shared.artefact.domain.entity.ArtefactMetaData
import ru.kamanin.nstu.graduate.thesis.shared.artefact.domain.repository.ArtefactRepository
import javax.inject.Inject

class GetArtefactMetaDataUseCase @Inject constructor(
	private val repository: ArtefactRepository
) {

	suspend operator fun invoke(artefactId: Long): ArtefactMetaData =
		repository.getMetaData(artefactId)
}