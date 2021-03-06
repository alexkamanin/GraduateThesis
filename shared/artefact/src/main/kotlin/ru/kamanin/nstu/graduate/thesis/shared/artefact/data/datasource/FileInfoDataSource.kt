package ru.kamanin.nstu.graduate.thesis.shared.artefact.data.datasource

import android.content.ContentResolver
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.OpenableColumns
import android.webkit.MimeTypeMap
import androidx.core.net.toFile
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import ru.kamanin.nstu.graduate.thesis.shared.artefact.domain.entity.FileInfo
import ru.kamanin.nstu.graduate.thesis.utils.coroutines.dispatcher.ioDispatcher
import javax.inject.Inject

interface FileInfoDataSource {

	suspend fun get(uri: Uri): FileInfo
}

class FileInfoDataSourceImpl @Inject constructor(
	@ioDispatcher private val ioDispatcher: CoroutineDispatcher,
	@ApplicationContext private val context: Context
) : FileInfoDataSource {

	override suspend fun get(uri: Uri): FileInfo =
		withContext(ioDispatcher) {
			when (uri.scheme) {
				ContentResolver.SCHEME_CONTENT -> getFromContentResolver(uri)
				ContentResolver.SCHEME_FILE    -> getFromFile(uri)
				else                           -> throw IllegalStateException("Unsupported scheme in URI: $uri")
			}
		}

	private fun getFromContentResolver(uri: Uri): FileInfo =
		context.contentResolver.query(uri, null, null, null, null)
			?.use { cursor -> retrieveFileInfo(cursor, uri) }
			?: throw IllegalStateException("Content provider returns null")

	private fun retrieveFileInfo(cursor: Cursor, uri: Uri): FileInfo {
		cursor.moveToFirst()

		val nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
		val sizeIndex = cursor.getColumnIndex(OpenableColumns.SIZE)

		val name = cursor.getString(nameIndex).dropExtensionIfExist()

		val size = cursor.getLong(sizeIndex)
		val type = context.contentResolver.getType(uri)

		val extension = MimeTypeMap.getSingleton()
			.getExtensionFromMimeType(type)

		return FileInfo(name, extension, size)
	}

	private fun getFromFile(uri: Uri): FileInfo {
		val file = uri.toFile()
		val name = file.name.dropExtensionIfExist()
		val extension = file.extension
		val size = file.length()

		return FileInfo(name, extension, size)
	}

	private fun String.dropExtensionIfExist(): String =
		substringBeforeLast(".")
}