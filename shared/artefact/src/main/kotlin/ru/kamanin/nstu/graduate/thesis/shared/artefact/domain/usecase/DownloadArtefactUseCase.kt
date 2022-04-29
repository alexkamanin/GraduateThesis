package ru.kamanin.nstu.graduate.thesis.shared.artefact.domain.usecase

import android.net.Uri
import ru.kamanin.nstu.graduate.thesis.shared.artefact.domain.entity.ArtefactMetaData
import ru.kamanin.nstu.graduate.thesis.shared.artefact.domain.repository.ArtefactRepository
import javax.inject.Inject

class DownloadArtefactUseCase @Inject constructor(
	private val repository: ArtefactRepository
) {

	suspend operator fun invoke(artefact: ArtefactMetaData): Uri =
		repository.download(artefact)
}