package ru.kamanin.nstu.graduate.thesis.shared.artefact.data.repository

import android.net.Uri
import ru.kamanin.nstu.graduate.thesis.shared.artefact.data.datasource.ArtefactDataSource
import ru.kamanin.nstu.graduate.thesis.shared.artefact.data.datasource.DocumentDataSource
import ru.kamanin.nstu.graduate.thesis.shared.artefact.data.datasource.MediaDataSource
import ru.kamanin.nstu.graduate.thesis.shared.artefact.data.mapper.toEntity
import ru.kamanin.nstu.graduate.thesis.shared.artefact.domain.entity.ArtefactMetaData
import ru.kamanin.nstu.graduate.thesis.shared.artefact.domain.repository.ArtefactRepository
import javax.inject.Inject

class ArtefactRepositoryImpl @Inject constructor(
	private val mediaDataSource: MediaDataSource,
	private val documentDataSource: DocumentDataSource,
	private val artefactDataSource: ArtefactDataSource,
) : ArtefactRepository {

	override suspend fun getMetaData(artefactId: Long): ArtefactMetaData =
		artefactDataSource.getMetaData(artefactId).toEntity()

	override suspend fun upload(uri: Uri): ArtefactMetaData =
		artefactDataSource.set(uri).toEntity()

	override suspend fun download(artefact: ArtefactMetaData): Uri =
		getLocal(artefact) ?: getRemote(artefact)

	private suspend fun getLocal(artefact: ArtefactMetaData): Uri? {
		return when {
			artefact.isMedia -> mediaDataSource.get(artefact)
			else             -> documentDataSource.get(artefact)
		}
	}

	private suspend fun getRemote(artefact: ArtefactMetaData): Uri {
		val responseBody = artefactDataSource.get(artefact.id)

		return when {
			artefact.isMedia -> mediaDataSource.set(artefact, responseBody)
			else             -> documentDataSource.set(artefact, responseBody)
		}
	}
}