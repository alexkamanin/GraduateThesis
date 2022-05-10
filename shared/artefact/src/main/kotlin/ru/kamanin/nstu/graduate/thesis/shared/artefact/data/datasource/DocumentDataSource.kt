package ru.kamanin.nstu.graduate.thesis.shared.artefact.data.datasource

import android.content.Context
import android.net.Uri
import androidx.documentfile.provider.DocumentFile
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import ru.kamanin.nstu.graduate.thesis.shared.artefact.domain.entity.ArtefactMetaData
import ru.kamanin.nstu.graduate.thesis.utils.coroutines.dispatcher.ioDispatcher
import java.io.FileOutputStream
import javax.inject.Inject

interface DocumentDataSource {

	suspend fun set(artefact: ArtefactMetaData, responseBody: ResponseBody): Uri

	suspend fun get(artefact: ArtefactMetaData): Uri?
}

class DocumentDataSourceImpl @Inject constructor(
	@ioDispatcher private val ioDispatcher: CoroutineDispatcher,
	@ApplicationContext private val context: Context,
	private val documentTreeDataSource: DocumentTreeDataSource
) : DocumentDataSource {

	private companion object {

		const val WRITE_MODE = "w"
	}

	override suspend fun set(artefact: ArtefactMetaData, responseBody: ResponseBody): Uri =
		withContext(ioDispatcher) {
			val documentDirectory = DocumentFile.fromTreeUri(context, documentTreeDataSource.get())
				?: throw IllegalStateException("Cannot find document uri tree")
			val document = documentDirectory.createFile(artefact.mimeType, artefact.savedName)
				?: throw IllegalStateException("Cannot create file '${artefact.fullName} in directory '${documentDirectory.name}'")

			responseBody.byteStream().use { inputStream ->
				context.contentResolver.openFileDescriptor(document.uri, WRITE_MODE)
					?.use { parcelFileDescriptor ->
						FileOutputStream(parcelFileDescriptor.fileDescriptor)
							.use { outputStream ->
								inputStream.copyTo(outputStream)
							}
					} ?: throw IllegalStateException("Cannot open file descriptor")
			}

			document.uri
		}

	override suspend fun get(artefact: ArtefactMetaData): Uri? =
		withContext(ioDispatcher) {
			val documentDirectory = DocumentFile.fromTreeUri(context, documentTreeDataSource.get())
				?: throw IllegalStateException("Cannot find document uri tree")

			val file = documentDirectory.findFile(artefact.savedName)
			file?.uri
		}
}