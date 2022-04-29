package ru.kamanin.nstu.graduate.thesis.shared.artefact.domain.repository

import android.net.Uri

interface DocumentTreeRepository {

	suspend fun set(uri: Uri)

	suspend fun get(): Uri

	suspend fun isPresent(): Boolean
}