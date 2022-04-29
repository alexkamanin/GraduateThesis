package ru.kamanin.nstu.graduate.thesis.shared.artefact.data.repository

import android.net.Uri
import ru.kamanin.nstu.graduate.thesis.shared.artefact.data.datasource.DocumentTreeDataSource
import ru.kamanin.nstu.graduate.thesis.shared.artefact.domain.repository.DocumentTreeRepository
import javax.inject.Inject

class DocumentTreeRepositoryImpl @Inject constructor(
	private val dataSource: DocumentTreeDataSource
) : DocumentTreeRepository {

	override suspend fun set(uri: Uri) {
		dataSource.set(uri)
	}

	override suspend fun get(): Uri =
		dataSource.get()

	override suspend fun isPresent(): Boolean =
		dataSource.isPresent()
}