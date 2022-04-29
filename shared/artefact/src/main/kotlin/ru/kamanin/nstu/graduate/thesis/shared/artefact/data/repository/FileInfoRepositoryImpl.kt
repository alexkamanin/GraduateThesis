package ru.kamanin.nstu.graduate.thesis.shared.artefact.data.repository

import android.net.Uri
import ru.kamanin.nstu.graduate.thesis.shared.artefact.data.datasource.FileInfoDataSource
import ru.kamanin.nstu.graduate.thesis.shared.artefact.domain.entity.FileInfo
import ru.kamanin.nstu.graduate.thesis.shared.artefact.domain.repository.FileInfoRepository
import javax.inject.Inject

class FileInfoRepositoryImpl @Inject constructor(
	private val dataSource: FileInfoDataSource
) : FileInfoRepository {

	override suspend fun get(uri: Uri): FileInfo =
		dataSource.get(uri)
}