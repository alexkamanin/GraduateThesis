package ru.kamanin.nstu.graduate.thesis.di.network.live

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import ru.kamanin.nstu.graduate.thesis.di.network.Authorized
import ru.kamanin.nstu.graduate.thesis.di.network.NotAuthorized
import ru.kamanin.nstu.graduate.thesis.shared.session.data.interceptor.SessionInterceptor
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

	@Provides
	@Singleton
	@Authorized
	fun provideAuthorizedOkHttpClient(
		httpLoggingInterceptor: HttpLoggingInterceptor,
		sessionInterceptor: SessionInterceptor
	): OkHttpClient =
		OkHttpClient
			.Builder()
			.addInterceptor(httpLoggingInterceptor)
			.addInterceptor(sessionInterceptor)
			.build()

	@Provides
	@Singleton
	@NotAuthorized
	fun provideNotAuthorizedOkHttpClient(
		httpLoggingInterceptor: HttpLoggingInterceptor,
	): OkHttpClient =
		OkHttpClient
			.Builder()
			.addInterceptor(httpLoggingInterceptor)
			.build()
}