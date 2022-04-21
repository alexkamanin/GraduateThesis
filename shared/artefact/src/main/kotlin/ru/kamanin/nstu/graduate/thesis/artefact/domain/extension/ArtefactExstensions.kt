package ru.kamanin.nstu.graduate.thesis.artefact.domain.extension

import androidx.annotation.DrawableRes
import ru.kamanin.nstu.graduate.thesis.artefact.R
import ru.kamanin.nstu.graduate.thesis.artefact.domain.entity.Artefact
import ru.kamanin.nstu.graduate.thesis.artefact.domain.entity.Artefact.Extension.*
import kotlin.math.roundToInt

//TODO вынести в :component-ui
val Artefact.icon: Int
	@DrawableRes
	get() =
		when (extension) {
			PNG  -> R.drawable.ic_png
			JPG  -> R.drawable.ic_jpg
			JPEG -> R.drawable.ic_jpeg
			TXT  -> R.drawable.ic_txt
			DOC  -> R.drawable.ic_doc
			DOCX -> R.drawable.ic_docx
			PDF  -> R.drawable.ic_pdf
			XML  -> R.drawable.ic_xml
			ZIP  -> R.drawable.ic_zip
			RAR  -> R.drawable.ic_rar
			else -> R.drawable.ic_unknown
		}

val Artefact.sizeInMegaByte: Int
	get() = (size * 9.54E-7).roundToInt()

val Artefact.sizeInKiloByte: Int
	get() = (size * 9.77E-4).roundToInt()