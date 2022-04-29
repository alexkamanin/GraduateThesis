package ru.kamanin.nstu.graduate.thesis.shared.artefact.domain.entity

data class FileInfo(
	val name: String,
	val extension: String?,
	val sizeInBytes: Long
) {

	val fullName: String
		get() = "$name$requireExtension"

	private val requireExtension: String
		get() = if (extension != null) ".$extension" else ""

	val isAllowed: Boolean
		get() {

			fun String.isSupportedType() =
				uppercase()
					.runCatching(ArtefactMetaData.Extension::valueOf)
					.map { true }
					.getOrDefault(false)

			return when {
				extension.isNullOrEmpty() -> true
				else                      -> extension.isSupportedType()
			}
		}
}