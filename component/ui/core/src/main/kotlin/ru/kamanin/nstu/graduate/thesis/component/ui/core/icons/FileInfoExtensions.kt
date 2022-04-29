package ru.kamanin.nstu.graduate.thesis.component.ui.core.icons

import androidx.annotation.DrawableRes
import ru.kamanin.nstu.graduate.thesis.component.ui.resources.R
import ru.kamanin.nstu.graduate.thesis.shared.artefact.domain.entity.ArtefactMetaData.Extension.*
import ru.kamanin.nstu.graduate.thesis.shared.artefact.domain.entity.FileInfo

val FileInfo.icon: Int
	@DrawableRes
	get() = when (extension?.uppercase()?.let(::valueOf)) {
		PNG  -> R.drawable.ic_artefact_png
		JPG  -> R.drawable.ic_artefact_jpg
		JPEG -> R.drawable.ic_artefact_jpeg
		TXT  -> R.drawable.ic_artefact_txt
		DOC  -> R.drawable.ic_artefact_doc
		DOCX -> R.drawable.ic_artefact_docx
		PDF  -> R.drawable.ic_artefact_pdf
		XML  -> R.drawable.ic_artefact_xml
		ZIP  -> R.drawable.ic_artefact_zip
		RAR  -> R.drawable.ic_artefact_rar
		else -> R.drawable.ic_artefact_unknown
	}