package ru.kamanin.nstu.graduate.thesis.shared.artefact.domain.usecase

import android.os.Build
import javax.inject.Inject

class ShouldAskWriteStoragePermission @Inject constructor() {

	operator fun invoke(): Boolean =
		Build.VERSION.SDK_INT <= Build.VERSION_CODES.P
}