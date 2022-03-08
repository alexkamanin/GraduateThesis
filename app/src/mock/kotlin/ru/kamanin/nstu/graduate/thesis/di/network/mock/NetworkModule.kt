package ru.kamanin.nstu.graduate.thesis.di.network.mock

import android.content.Context
import com.example.retrofitmockinterceptor.MockInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import ru.kamanin.nstu.graduate.thesis.di.network.Authorized
import ru.kamanin.nstu.graduate.thesis.di.network.NotAuthorized
import ru.kamanin.nstu.graduate.thesis.di.network.mock.account.PostChangePasswordRequestMock
import ru.kamanin.nstu.graduate.thesis.di.network.mock.exam.GetExamListRequestMock
import ru.kamanin.nstu.graduate.thesis.di.network.mock.session.PostLoginRequestMock
import ru.kamanin.nstu.graduate.thesis.shared.session.data.interceptor.SessionInterceptor
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

	private val mockRequests = listOf(
		PostLoginRequestMock(),
		GetExamListRequestMock(),
		PostChangePasswordRequestMock()
	)

	@Provides
	@Singleton
	fun provideMockInterceptor(@ApplicationContext context: Context): MockInterceptor =
		MockInterceptor(context.resources, mockRequests)

	@Provides
	@Singleton
	@Authorized
	fun provideAuthorizedOkHttpClient(
		httpLoggingInterceptor: HttpLoggingInterceptor,
		sessionInterceptor: SessionInterceptor,
		mockInterceptor: MockInterceptor,
	): OkHttpClient =
		OkHttpClient
			.Builder()
			.addInterceptor(httpLoggingInterceptor)
			.addInterceptor(sessionInterceptor)
			.addInterceptor(mockInterceptor)
			.build()

	@Provides
	@Singleton
	@NotAuthorized
	fun provideNotAuthorizedOkHttpClient(
		httpLoggingInterceptor: HttpLoggingInterceptor,
		mockInterceptor: MockInterceptor,
	): OkHttpClient =
		OkHttpClient
			.Builder()
			.addInterceptor(httpLoggingInterceptor)
			.addInterceptor(mockInterceptor)
			.build()
}