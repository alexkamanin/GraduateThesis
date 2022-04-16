package ru.kamanin.nstu.graduate.thesis.artefact.domain.usecase

import android.net.Uri
import ru.kamanin.nstu.graduate.thesis.artefact.domain.repository.ArtefactRepository
import javax.inject.Inject

class DownloadArtefactUseCase @Inject constructor(
	private val repository: ArtefactRepository
) {

	suspend operator fun invoke(artefactId: Long): Uri =
		repository.download(artefactId)
}