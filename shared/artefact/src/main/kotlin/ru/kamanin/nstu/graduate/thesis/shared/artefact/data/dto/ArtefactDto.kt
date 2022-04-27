package ru.kamanin.nstu.graduate.thesis.shared.artefact.data.dto

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ArtefactDto(
	val id: Long,
	val fileSize: Long,
	val artefactType: String,
	val fileName: String
)