package ru.kamanin.nstu.graduate.thesis.shared.artefact.domain.repository

import android.net.Uri
import ru.kamanin.nstu.graduate.thesis.shared.artefact.domain.entity.Artefact

interface ArtefactRepository {

	suspend fun upload(uri: Uri): Artefact

	suspend fun getInfo(artefactId: Long): Artefact

	suspend fun download(artefactId: Long): Uri
}