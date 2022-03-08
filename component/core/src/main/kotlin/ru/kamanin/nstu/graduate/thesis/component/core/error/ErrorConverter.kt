package ru.kamanin.nstu.graduate.thesis.component.core.error

import retrofit2.HttpException
import java.net.ConnectException
import java.net.UnknownHostException
import javax.inject.Inject

class ErrorConverter @Inject constructor() {

	fun convert(throwable: Throwable): ErrorState =
		when (throwable) {
			is HttpException        -> handleHttpException(throwable)
			is ConnectException,
			is UnknownHostException -> ErrorState.LostConnection
			else                    -> ErrorState.Unknown
		}

	private fun handleHttpException(httpException: HttpException): ErrorState =
		when (httpException.code()) {
			400         -> ErrorState.BadParam
			401         -> ErrorState.NotAuthorized
			404         -> ErrorState.NotFound
			in 500..526 -> ErrorState.ServiceUnavailable
			else        -> ErrorState.Unknown
		}
}