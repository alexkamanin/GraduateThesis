package ru.kamanin.nstu.graduate.thesis.di.network.develop

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.github.alexkamanin.mockcept.Mockcept
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import ru.kamanin.nstu.graduate.thesis.di.network.Authorized
import ru.kamanin.nstu.graduate.thesis.di.network.NotAuthorized
import ru.kamanin.nstu.graduate.thesis.di.network.develop.handlers.*
import ru.kamanin.nstu.graduate.thesis.shared.session.data.interceptor.SessionInterceptor
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

	private val handlers = listOf(
		AccountHandler,
		AnswerHandler,
		ArtefactHandler,
		ChangePasswordHandler,
		ChatHandler,
		ExamHandler,
		SessionHandler,
		TaskHandler,
		TicketFoundHandler,
	)

	@Provides
	@Singleton
	fun provideMockcept(@ApplicationContext context: Context): Mockcept =
		Mockcept(context, handlers)

	@Provides
	@Singleton
	@Authorized
	fun provideAuthorizedOkHttpClient(
		httpLoggingInterceptor: HttpLoggingInterceptor,
		sessionInterceptor: SessionInterceptor,
		mockcept: Mockcept,
	): OkHttpClient =
		OkHttpClient
			.Builder()
			.addInterceptor(httpLoggingInterceptor)
			.addInterceptor(sessionInterceptor)
			.addInterceptor(mockcept)
			.build()

	@Provides
	@Singleton
	@NotAuthorized
	fun provideNotAuthorizedOkHttpClient(
		httpLoggingInterceptor: HttpLoggingInterceptor,
		mockcept: Mockcept,
	): OkHttpClient =
		OkHttpClient
			.Builder()
			.addInterceptor(httpLoggingInterceptor)
			.addInterceptor(mockcept)
			.build()
}