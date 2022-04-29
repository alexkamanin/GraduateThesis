package ru.kamanin.nstu.graduate.thesis.shared.artefact.data.datasource

import android.net.Uri
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import ru.kamanin.nstu.graduate.thesis.shared.artefact.data.api.ArtefactApi
import ru.kamanin.nstu.graduate.thesis.shared.artefact.data.dto.ArtefactDto
import ru.kamanin.nstu.graduate.thesis.utils.coroutines.dispatcher.ioDispatcher
import javax.inject.Inject

interface ArtefactDataSource {

	suspend fun set(uri: Uri): ArtefactDto

	suspend fun get(artefactId: Long): ResponseBody

	suspend fun getMetaData(artefactId: Long): ArtefactDto
}

class ArtefactDataSourceImpl @Inject constructor(
	@ioDispatcher private val ioDispatcher: CoroutineDispatcher,
	private val fileInfoDataSource: FileInfoDataSource,
	private val cacheDataSource: CacheDataSource,
	private val api: ArtefactApi
) : ArtefactDataSource {

	private companion object {

		const val ARTEFACT_PART_NAME = "file"
		const val ARTEFACT_MEDIA_TYPE = "multipart/form-data"
	}

	override suspend fun set(uri: Uri): ArtefactDto =
		withContext(ioDispatcher) {
			val fileInfo = fileInfoDataSource.get(uri)
			val file = cacheDataSource.copy(uri, fileInfo.fullName)

			val requestFile = RequestBody.create(MediaType.parse(ARTEFACT_MEDIA_TYPE), file)
			val body = MultipartBody.Part.createFormData(ARTEFACT_PART_NAME, file.name, requestFile)

			api.upload(body).also {
				cacheDataSource.clear(fileInfo.fullName)
			}
		}

	override suspend fun get(artefactId: Long): ResponseBody =
		withContext(ioDispatcher) {
			api.download(artefactId)
		}

	override suspend fun getMetaData(artefactId: Long): ArtefactDto =
		withContext(ioDispatcher) {
			api.getMetaData(artefactId)
		}
}