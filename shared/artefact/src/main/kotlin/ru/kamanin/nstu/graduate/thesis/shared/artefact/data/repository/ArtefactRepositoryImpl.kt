package ru.kamanin.nstu.graduate.thesis.shared.artefact.data.repository

import android.content.Context
import android.net.Uri
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import ru.kamanin.nstu.graduate.thesis.shared.artefact.data.api.ArtefactApi
import ru.kamanin.nstu.graduate.thesis.shared.artefact.data.mapper.toEntity
import ru.kamanin.nstu.graduate.thesis.shared.artefact.domain.entity.Artefact
import ru.kamanin.nstu.graduate.thesis.shared.artefact.domain.repository.ArtefactRepository
import ru.kamanin.nstu.graduate.thesis.shared.artefact.domain.repository.FileInfoRepository
import ru.kamanin.nstu.graduate.thesis.utils.coroutines.dispatcher.ioDispatcher
import java.io.File
import java.io.InputStream
import javax.inject.Inject

class ArtefactRepositoryImpl @Inject constructor(
	@ioDispatcher private val ioDispatcher: CoroutineDispatcher,
	private val api: ArtefactApi,
	private val fileInfoRepository: FileInfoRepository,
	@ApplicationContext private val context: Context
) : ArtefactRepository {

	private companion object {

		const val ARTEFACT_PART_NAME = "file"
		const val ARTEFACT_MEDIA_TYPE = "multipart/form-data"
	}

	override suspend fun upload(uri: Uri): Artefact {
		return withContext(ioDispatcher) {
			val fileInfo = fileInfoRepository.get(uri)

			val inputStream = requireNotNull(context.contentResolver.openInputStream(uri))
			val file = File(context.cacheDir, fileInfo.name)

			val newFile = file.apply(inputStream::copyTo)

			val requestFile = RequestBody.create(MediaType.parse(ARTEFACT_MEDIA_TYPE), newFile)
			val body = MultipartBody.Part.createFormData(ARTEFACT_PART_NAME, newFile.name + "." + fileInfo.extension, requestFile)

			api.upload(body).toEntity()
		}
	}

	override suspend fun getInfo(artefactId: Long): Artefact =
		withContext(ioDispatcher) {
			api.getInfo(artefactId).toEntity()
		}

	override suspend fun download(artefactId: Long): Uri {
		withContext(ioDispatcher) {
			api.download(artefactId)
		}
		return Uri.EMPTY
	}
}

fun InputStream.copyTo(file: File) {
	use { input ->
		file.outputStream().use { fileOut ->
			input.copyTo(fileOut)
		}
	}
}