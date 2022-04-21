package ru.kamanin.nstu.graduate.thesis.artefact.domain.entity

data class Artefact(
	val id: Long,
	val size: Long,
	val extension: Extension,
	val fullName: String
) {

	enum class Extension {
		UNKNOWN,
		PNG,
		JPG,
		JPEG,
		TXT,
		DOC,
		DOCX,
		PDF,
		XML,
		ZIP,
		RAR
	}
}