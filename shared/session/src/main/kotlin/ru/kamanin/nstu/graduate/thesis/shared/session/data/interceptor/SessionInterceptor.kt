package ru.kamanin.nstu.graduate.thesis.shared.session.data.interceptor

import okhttp3.Interceptor
import okhttp3.Response
import ru.kamanin.nstu.graduate.thesis.shared.api.headers.Headers.X_Access_Token
import ru.kamanin.nstu.graduate.thesis.shared.session.domain.repository.LocalSessionRepository
import javax.inject.Inject

class SessionInterceptor @Inject constructor(
	private val sessionRepository: LocalSessionRepository
) : Interceptor {

	override fun intercept(chain: Interceptor.Chain): Response {
		val request = chain.request()
		val token = sessionRepository.get().token

		val authRequest = request
			.newBuilder()
			.addHeader(X_Access_Token, token)
			.build()

		return chain.proceed(authRequest)
	}
}