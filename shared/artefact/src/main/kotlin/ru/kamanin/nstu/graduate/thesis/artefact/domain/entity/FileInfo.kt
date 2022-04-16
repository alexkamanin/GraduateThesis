package ru.kamanin.nstu.graduate.thesis.artefact.domain.entity

data class FileInfo(
	val name: String,
	val extension: String?,
	val sizeInBytes: Long
)