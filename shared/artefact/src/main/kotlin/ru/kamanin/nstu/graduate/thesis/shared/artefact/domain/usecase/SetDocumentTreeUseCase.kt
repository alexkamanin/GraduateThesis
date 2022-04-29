package ru.kamanin.nstu.graduate.thesis.shared.artefact.domain.usecase

import android.net.Uri
import ru.kamanin.nstu.graduate.thesis.shared.artefact.domain.repository.DocumentTreeRepository
import javax.inject.Inject

class SetDocumentTreeUseCase @Inject constructor(
	private val repository: DocumentTreeRepository
) {

	suspend operator fun invoke(uri: Uri) {
		repository.set(uri)
	}
}