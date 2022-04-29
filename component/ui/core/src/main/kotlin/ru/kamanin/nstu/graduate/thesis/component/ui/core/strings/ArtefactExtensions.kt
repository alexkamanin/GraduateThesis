package ru.kamanin.nstu.graduate.thesis.component.ui.core.strings

import android.content.Context
import ru.kamanin.nstu.graduate.thesis.component.ui.core.R
import ru.kamanin.nstu.graduate.thesis.shared.artefact.domain.entity.ArtefactMetaData

fun artefactSingleLineType(context: Context, artefact: ArtefactMetaData): String =
	when {
		artefact.sizeInMegaByte > 0 -> context.getString(R.string.hint_artefact_type_mb, artefact.sizeInMegaByte, artefact.extension)
		else                        -> context.getString(R.string.hint_artefact_type_kb, artefact.sizeInKiloByte, artefact.extension)
	}