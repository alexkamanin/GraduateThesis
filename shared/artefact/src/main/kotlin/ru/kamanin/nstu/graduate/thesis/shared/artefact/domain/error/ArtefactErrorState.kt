package ru.kamanin.nstu.graduate.thesis.shared.artefact.domain.error

import ru.kamanin.nstu.graduate.thesis.shared.artefact.domain.entity.FileInfo

sealed interface ArtefactErrorState {

	object DownloadError : ArtefactErrorState

	object UploadError : ArtefactErrorState

	object NotFoundError : ArtefactErrorState

	data class UnsupportedError(val file: FileInfo) : ArtefactErrorState
}