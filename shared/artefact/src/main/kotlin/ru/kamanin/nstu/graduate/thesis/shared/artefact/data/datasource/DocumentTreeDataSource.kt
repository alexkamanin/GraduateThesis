package ru.kamanin.nstu.graduate.thesis.shared.artefact.data.datasource

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.documentfile.provider.DocumentFile
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import ru.kamanin.nstu.graduate.thesis.utils.coroutines.dispatcher.ioDispatcher
import javax.inject.Inject

interface DocumentTreeDataSource {

	suspend fun set(uri: Uri)

	suspend fun get(): Uri

	suspend fun isPresent(): Boolean
}

class DocumentTreeDataSourceImpl @Inject constructor(
	@ioDispatcher private val ioDispatcher: CoroutineDispatcher,
	@ApplicationContext private val context: Context
) : DocumentTreeDataSource {

	private companion object {

		const val DOCUMENT_TREE_PREFERENCES = "documentTreePreferences"
		const val DOCUMENT_TREE_VALUE = "documentTreeValue"
		const val DOCUMENT_TREE_MODE = Intent.FLAG_GRANT_READ_URI_PERMISSION + Intent.FLAG_GRANT_WRITE_URI_PERMISSION
	}

	private val documentTreePreferences by lazy {
		context.getSharedPreferences(DOCUMENT_TREE_PREFERENCES, Context.MODE_PRIVATE)
	}

	override suspend fun set(uri: Uri) {
		withContext(ioDispatcher) {
			context.contentResolver.takePersistableUriPermission(uri, DOCUMENT_TREE_MODE)
			documentTreePreferences
				.edit()
				.putString(DOCUMENT_TREE_VALUE, uri.toString())
				.apply()
		}
	}

	override suspend fun get(): Uri =
		withContext(ioDispatcher) {
			documentTreePreferences.getString(DOCUMENT_TREE_VALUE, null)
				?.let(Uri::parse)
				?: throw IllegalStateException("Document tree not initialized")
		}

	override suspend fun isPresent(): Boolean =
		withContext(ioDispatcher) {
			runCatching {
				val documentDirectory = DocumentFile.fromTreeUri(context, get())
				documentDirectory?.exists() ?: false
			}.getOrDefault(false)
		}
}