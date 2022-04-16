package ru.kamanin.nstu.graduate.thesis.di.network.develop.handlers

import io.github.alexkamanin.mockcept.handler.handlePath
import io.github.alexkamanin.mockcept.response.StatusCode

val ChangePasswordHandler = handlePath("/account/change-password") {

	post {
		status = StatusCode.OK
	}
}