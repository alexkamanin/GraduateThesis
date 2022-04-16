package ru.kamanin.nstu.graduate.thesis.artefact.domain.usecase

import android.net.Uri
import ru.kamanin.nstu.graduate.thesis.artefact.domain.entity.Artefact
import ru.kamanin.nstu.graduate.thesis.artefact.domain.repository.ArtefactRepository
import javax.inject.Inject

class UploadArtefactUseCase @Inject constructor(
	private val repository: ArtefactRepository
) {

	suspend operator fun invoke(uri: Uri): Artefact =
		repository.upload(uri)
}