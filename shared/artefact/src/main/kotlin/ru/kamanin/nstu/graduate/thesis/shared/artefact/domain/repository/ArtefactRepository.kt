package ru.kamanin.nstu.graduate.thesis.shared.artefact.domain.repository

import android.net.Uri
import ru.kamanin.nstu.graduate.thesis.shared.artefact.domain.entity.ArtefactMetaData

interface ArtefactRepository {

	suspend fun getMetaData(artefactId: Long): ArtefactMetaData

	suspend fun upload(uri: Uri): ArtefactMetaData

	suspend fun download(artefact: ArtefactMetaData): Uri
}