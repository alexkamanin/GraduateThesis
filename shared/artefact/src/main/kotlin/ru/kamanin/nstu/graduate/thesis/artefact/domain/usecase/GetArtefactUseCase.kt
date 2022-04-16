package ru.kamanin.nstu.graduate.thesis.artefact.domain.usecase

import ru.kamanin.nstu.graduate.thesis.artefact.domain.entity.Artefact
import ru.kamanin.nstu.graduate.thesis.artefact.domain.repository.ArtefactRepository
import javax.inject.Inject

class GetArtefactUseCase @Inject constructor(
	private val repository: ArtefactRepository
) {

	suspend operator fun invoke(artefactId: Long): Artefact =
		repository.getInfo(artefactId)
}