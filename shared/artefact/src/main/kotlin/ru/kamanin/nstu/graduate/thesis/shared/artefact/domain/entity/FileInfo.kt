package ru.kamanin.nstu.graduate.thesis.shared.artefact.domain.entity

data class FileInfo(
	val name: String,
	val extension: String?,
	val sizeInBytes: Long
) {

	val fullName: String
		get() = "{$name}${requireExtension()}"

	private fun requireExtension() =
		if (extension != null) ".$extension" else ""
}