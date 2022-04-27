package ru.kamanin.nstu.graduate.thesis.di.network.api

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.create
import ru.kamanin.nstu.graduate.thesis.di.network.Authorized
import ru.kamanin.nstu.graduate.thesis.di.network.NotAuthorized
import ru.kamanin.nstu.graduate.thesis.shared.artefact.data.api.ArtefactApi
import ru.kamanin.nstu.graduate.thesis.shared.chat.data.api.ChatApi
import ru.kamanin.nstu.graduate.thesis.shared.exam.data.api.ExamApi
import ru.kamanin.nstu.graduate.thesis.shared.session.data.api.SessionApi
import javax.inject.Singleton
import ru.kamanin.nstu.graduate.thesis.shared.account.data.api.authorized.AccountApi as AuthorizedAccountApi
import ru.kamanin.nstu.graduate.thesis.shared.account.data.api.notauthorized.AccountApi as NotAuthorizedAccountApi

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

	@Provides
	@Singleton
	fun provideSessionApi(@NotAuthorized retrofit: Retrofit): SessionApi =
		retrofit.create()

	@Provides
	@Singleton
	fun provideNotAuthorizedAccountApi(@NotAuthorized retrofit: Retrofit): NotAuthorizedAccountApi =
		retrofit.create()

	@Provides
	@Singleton
	fun provideAuthorizedAccountApi(@Authorized retrofit: Retrofit): AuthorizedAccountApi =
		retrofit.create()

	@Provides
	@Singleton
	fun provideExamApi(@Authorized retrofit: Retrofit): ExamApi =
		retrofit.create()

	@Provides
	@Singleton
	fun provideTicketApi(@Authorized retrofit: Retrofit): ru.kamanin.nstu.graduate.thesis.shared.ticket.data.api.TicketApi =
		retrofit.create()

	@Provides
	@Singleton
	fun provideArtefactApi(@Authorized retrofit: Retrofit): ArtefactApi =
		retrofit.create()

	@Provides
	@Singleton
	fun provideChatApi(@Authorized retrofit: Retrofit): ChatApi =
		retrofit.create()
}