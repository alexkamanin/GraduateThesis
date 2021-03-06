package ru.kamanin.nstu.graduate.thesis.shared.artefact.domain.usecase

import android.net.Uri
import ru.kamanin.nstu.graduate.thesis.shared.artefact.domain.entity.ArtefactMetaData
import ru.kamanin.nstu.graduate.thesis.shared.artefact.domain.repository.ArtefactRepository
import javax.inject.Inject

class UploadArtefactUseCase @Inject constructor(
	private val repository: ArtefactRepository
) {

	suspend operator fun invoke(uri: Uri): ArtefactMetaData =
		repository.upload(uri)
}