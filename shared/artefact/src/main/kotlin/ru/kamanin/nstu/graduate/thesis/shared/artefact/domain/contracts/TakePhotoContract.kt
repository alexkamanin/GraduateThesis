package ru.kamanin.nstu.graduate.thesis.shared.artefact.domain.contracts

import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.activity.result.contract.ActivityResultContract

class TakePhotoContract : ActivityResultContract<Unit, Uri?>() {

	private companion object {

		const val TYPE = "jpg"
		const val FORMAT_NAME = "IMAGE_%d.$TYPE"
		const val MIME_TYPE = "image/$TYPE"

		fun getFormatName(): String =
			String.format(FORMAT_NAME, System.currentTimeMillis())
	}

	private val collection: Uri =
		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q)
			MediaStore.Images.Media.EXTERNAL_CONTENT_URI
		else
			MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL)

	private var preparedImageUri: Uri? = null

	override fun createIntent(context: Context, input: Unit?): Intent {
		val resolver = context.contentResolver

		val contentValues = ContentValues().apply {
			put(MediaStore.MediaColumns.DISPLAY_NAME, getFormatName())
			put(MediaStore.MediaColumns.MIME_TYPE, MIME_TYPE)
		}

		preparedImageUri = resolver.insert(collection, contentValues) ?: throw IllegalStateException("Cannot insert uri for image")

		val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
		intent.putExtra(MediaStore.EXTRA_OUTPUT, preparedImageUri)

		return intent
	}

	override fun parseResult(resultCode: Int, intent: Intent?): Uri? =
		if (resultCode == Activity.RESULT_OK) {
			val uri = requireNotNull(preparedImageUri)
			preparedImageUri = null
			uri
		} else {
			null
		}
}