package ru.kamanin.nstu.graduate.thesis.di.network.develop.handlers

import io.github.alexkamanin.mockcept.handler.handlePath
import io.github.alexkamanin.mockcept.response.StatusCode
import ru.kamanin.nstu.graduate.thesis.R

val ArtefactHandler = handlePath("/artefact") {

	"/5161/info".get {
		status = StatusCode.OK
		body = R.raw.get_artefact_info_png
	}
	"/5162/info".get {
		status = StatusCode.OK
		body = R.raw.get_artefact_info_pdf
	}
	"/5163/info".get {
		status = StatusCode.OK
		body = R.raw.get_artefact_info_docx
	}
}