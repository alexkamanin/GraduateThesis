package ru.kamanin.nstu.graduate.thesis.shared.artefact.domain.usecase

import android.net.Uri
import ru.kamanin.nstu.graduate.thesis.shared.artefact.domain.entity.FileInfo
import ru.kamanin.nstu.graduate.thesis.shared.artefact.domain.repository.FileInfoRepository
import javax.inject.Inject

class GetFileInfoUseCase @Inject constructor(
	private val repository: FileInfoRepository
) {

	suspend operator fun invoke(uri: Uri): FileInfo =
		repository.get(uri)
}