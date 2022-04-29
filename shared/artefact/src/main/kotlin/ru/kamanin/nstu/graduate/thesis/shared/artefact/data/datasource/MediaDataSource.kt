package ru.kamanin.nstu.graduate.thesis.shared.artefact.data.datasource

import android.content.ContentUris
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import ru.kamanin.nstu.graduate.thesis.shared.artefact.domain.entity.ArtefactMetaData
import ru.kamanin.nstu.graduate.thesis.utils.coroutines.dispatcher.ioDispatcher
import javax.inject.Inject

interface MediaDataSource {

	suspend fun set(artefact: ArtefactMetaData, responseBody: ResponseBody): Uri

	suspend fun get(artefact: ArtefactMetaData): Uri?
}

class MediaDataSourceImpl @Inject constructor(
	@ioDispatcher private val ioDispatcher: CoroutineDispatcher,
	@ApplicationContext private val context: Context
) : MediaDataSource {

	private companion object {

		val collection: Uri =
			if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q)
				MediaStore.Images.Media.EXTERNAL_CONTENT_URI
			else
				MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL)
	}

	override suspend fun set(artefact: ArtefactMetaData, responseBody: ResponseBody): Uri =
		withContext(ioDispatcher) {
			val resolver = context.contentResolver

			val contentValues = ContentValues().apply {
				put(MediaStore.MediaColumns.DISPLAY_NAME, artefact.fullName)
				put(MediaStore.MediaColumns.MIME_TYPE, artefact.mimeType)
			}

			val uri: Uri? = resolver.insert(collection, contentValues)
			val requiredUri = requireNotNull(uri)

			responseBody.byteStream().use { inputStream ->
				resolver.openOutputStream(requiredUri).use { outputStream ->
					inputStream.copyTo(requireNotNull(outputStream))
				}
			}

			requiredUri
		}

	override suspend fun get(artefact: ArtefactMetaData): Uri? =
		withContext(ioDispatcher) {
			val projection = arrayOf(
				MediaStore.Images.Media._ID,
				MediaStore.Images.Media.DISPLAY_NAME,
			)

			context.contentResolver.query(collection, projection, null, null, null)
				?.use { cursor -> retrieveMediaUri(cursor, collection, artefact.fullName) }
		}

	private fun retrieveMediaUri(cursor: Cursor, collection: Uri, mediaName: String): Uri? {
		val mediaIdColumn = cursor.getColumnIndex(MediaStore.Images.Media._ID)
		val displayNameColumn = cursor.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME)

		while (cursor.moveToNext()) {
			val imageId = cursor.getLong(mediaIdColumn)
			val displayName = cursor.getString(displayNameColumn)

			if (displayName == mediaName) {
				return ContentUris.withAppendedId(collection, imageId)
			}
		}

		return null
	}
}