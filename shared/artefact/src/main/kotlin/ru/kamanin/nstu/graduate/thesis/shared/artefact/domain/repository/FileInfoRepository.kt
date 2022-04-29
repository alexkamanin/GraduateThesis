package ru.kamanin.nstu.graduate.thesis.shared.artefact.domain.repository

import android.net.Uri
import ru.kamanin.nstu.graduate.thesis.shared.artefact.domain.entity.FileInfo

interface FileInfoRepository {

	suspend fun get(uri: Uri): FileInfo
}