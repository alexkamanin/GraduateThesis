package ru.kamanin.nstu.graduate.thesis.di.network.develop.handlers

import io.github.alexkamanin.mockcept.handler.handlePath
import io.github.alexkamanin.mockcept.response.StatusCode
import ru.kamanin.nstu.graduate.thesis.R

val SessionHandler = handlePath("/login") {

	post {
		status = StatusCode.OK
		body = R.raw.post_login_response
	}
}