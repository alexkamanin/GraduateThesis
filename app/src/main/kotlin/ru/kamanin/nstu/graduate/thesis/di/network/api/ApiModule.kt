package ru.kamanin.nstu.graduate.thesis.di.network.api

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.create
import ru.kamanin.nstu.graduate.thesis.di.network.Authorized
import ru.kamanin.nstu.graduate.thesis.di.network.NotAuthorized
import ru.kamanin.nstu.graduate.thesis.shared.account.data.api.AccountApi
import ru.kamanin.nstu.graduate.thesis.shared.exam.data.api.ExamApi
import ru.kamanin.nstu.graduate.thesis.shared.session.data.api.SessionApi
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

	@Provides
	@Singleton
	fun provideSessionApi(@NotAuthorized retrofit: Retrofit): SessionApi =
		retrofit.create()

	@Provides
	@Singleton
	fun provideAccountApi(@NotAuthorized retrofit: Retrofit): AccountApi =
		retrofit.create()

	@Provides
	@Singleton
	fun provideExamApi(@Authorized retrofit: Retrofit): ExamApi =
		retrofit.create()
}