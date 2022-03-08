package ru.kamanin.nstu.graduate.thesis.di.network.mock.handlers

import io.github.alexkamanin.mockcept.handler.handlePath
import io.github.alexkamanin.mockcept.response.StatusCode
import ru.kamanin.nstu.graduate.thesis.R

val ExamHandler = handlePath("/student/ticket") {

	get {
		status = StatusCode.OK
		body = R.raw.get_exam_list_response
	}
}