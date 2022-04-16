package ru.kamanin.nstu.graduate.thesis.di.network.develop.handlers

import io.github.alexkamanin.mockcept.handler.handlePath
import io.github.alexkamanin.mockcept.response.StatusCode
import ru.kamanin.nstu.graduate.thesis.R

val TicketFoundHandler = handlePath("/ticket") {

	"/11/answer".get {
		status = StatusCode.OK
		body = R.raw.get_ticket
	}
	"/(10|12)/answer".get {
		status = StatusCode.BadRequest
	}
}