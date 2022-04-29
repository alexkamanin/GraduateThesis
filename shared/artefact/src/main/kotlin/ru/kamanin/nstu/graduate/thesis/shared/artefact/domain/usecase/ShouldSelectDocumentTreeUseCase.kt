package ru.kamanin.nstu.graduate.thesis.shared.artefact.domain.usecase

import ru.kamanin.nstu.graduate.thesis.shared.artefact.domain.repository.DocumentTreeRepository
import javax.inject.Inject

class ShouldSelectDocumentTreeUseCase @Inject constructor(
	private val repository: DocumentTreeRepository
) {

	suspend operator fun invoke(): Boolean =
		repository.isPresent().not()
}