package ru.kamanin.nstu.graduate.thesis.shared.artefact.domain.entity

import kotlin.math.roundToInt

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

	val sizeInMegaByte: Int
		get() = (size * 9.54E-7).roundToInt()

	val sizeInKiloByte: Int
		get() = (size * 9.77E-4).roundToInt()
}