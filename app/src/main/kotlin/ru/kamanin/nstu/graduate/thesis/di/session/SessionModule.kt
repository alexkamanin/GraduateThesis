package ru.kamanin.nstu.graduate.thesis.di.session

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.kamanin.nstu.graduate.thesis.shared.session.data.repository.LocalSessionRepositoryImpl
import ru.kamanin.nstu.graduate.thesis.shared.session.data.repository.RemoteSessionRepositoryImpl
import ru.kamanin.nstu.graduate.thesis.shared.session.data.repository.SessionConfigRepository
import ru.kamanin.nstu.graduate.thesis.shared.session.data.repository.SessionConfigRepositoryImpl
import ru.kamanin.nstu.graduate.thesis.shared.session.domain.repository.LocalSessionRepository
import ru.kamanin.nstu.graduate.thesis.shared.session.domain.repository.RemoteSessionRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface SessionModule {

	@Binds
	@Singleton
	fun bindRemoteSessionRepository(impl: RemoteSessionRepositoryImpl): RemoteSessionRepository

	@Binds
	@Singleton
	fun bindLocalSessionRepository(impl: LocalSessionRepositoryImpl): LocalSessionRepository

	@Binds
	@Singleton
	fun bindLocalSessionConfigRepository(impl: SessionConfigRepositoryImpl): SessionConfigRepository
}