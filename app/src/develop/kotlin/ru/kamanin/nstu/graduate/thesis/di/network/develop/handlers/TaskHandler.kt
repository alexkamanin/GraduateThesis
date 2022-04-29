package ru.kamanin.nstu.graduate.thesis.di.network.develop.handlers

import io.github.alexkamanin.mockcept.handler.handlePath
import io.github.alexkamanin.mockcept.response.StatusCode
import ru.kamanin.nstu.graduate.thesis.R

val TaskHandler = handlePath("/answer/[0-9]+/message") {

	get(
		"page" to 0,
		"size" to 20
	) {
		status = StatusCode.OK
		body = R.raw.get_messages_page_1
	}
	get(
		"page" to 1,
		"size" to 20
	) {
		status = StatusCode.OK
		body = R.raw.get_messages_page_2
	}
}