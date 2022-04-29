package ru.kamanin.nstu.graduate.thesis.shared.artefact.data.datasource

import android.content.Context
import android.net.Uri
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import ru.kamanin.nstu.graduate.thesis.utils.coroutines.dispatcher.ioDispatcher
import java.io.File
import java.io.InputStream
import javax.inject.Inject

interface CacheDataSource {

	suspend fun copy(uri: Uri, fileName: String): File

	suspend fun clear(fileName: String)
}

class CacheDataSourceImpl @Inject constructor(
	@ioDispatcher private val ioDispatcher: CoroutineDispatcher,
	@ApplicationContext private val context: Context
) : CacheDataSource {

	override suspend fun copy(uri: Uri, fileName: String): File =
		withContext(ioDispatcher) {
			val inputStream = requireNotNull(context.contentResolver.openInputStream(uri))
			val file = File(context.cacheDir, fileName)

			file.apply(inputStream::copyTo)
		}

	override suspend fun clear(fileName: String) {
		withContext(ioDispatcher) {
			File(context.cacheDir, fileName)
				.delete()
		}
	}
}

private fun InputStream.copyTo(file: File) {
	use { input ->
		file.outputStream().use { out ->
			input.copyTo(out)
		}
	}
}


