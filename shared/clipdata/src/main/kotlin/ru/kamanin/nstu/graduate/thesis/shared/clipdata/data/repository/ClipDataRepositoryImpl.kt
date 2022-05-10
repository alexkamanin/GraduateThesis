package ru.kamanin.nstu.graduate.thesis.shared.clipdata.data.repository

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import ru.kamanin.nstu.graduate.thesis.shared.clipdata.domain.repository.ClipDataRepository
import javax.inject.Inject

class ClipDataRepositoryImpl @Inject constructor(
	@ApplicationContext private val context: Context
) : ClipDataRepository {

	private companion object {

		const val CLIP_LABEL = "COPIED_TEXT"
	}

	private val clipboardManager = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager

	override fun set(text: String) {
		val clipData = ClipData.newPlainText(CLIP_LABEL, text)
		clipboardManager.setPrimaryClip(clipData)
	}
}