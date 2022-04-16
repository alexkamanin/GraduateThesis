package ru.kamanin.nstu.graduate.thesis.artefact.domain.repository

import android.net.Uri
import ru.kamanin.nstu.graduate.thesis.artefact.domain.entity.FileInfo

interface FileInfoRepository {

	fun get(uri: Uri): FileInfo
}