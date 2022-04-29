package ru.kamanin.nstu.graduate.thesis.shared.artefact.domain.entity

import android.net.Uri

data class Openable(
	val mimeType: String,
	val uri: Uri
)