package ru.kamanin.nstu.graduate.thesis.di.session

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import ru.kamanin.nstu.graduate.thesis.component.core.coroutines.dispatcher.ioDispatcher
import ru.kamanin.nstu.graduate.thesis.shared.session.data.api.SessionApi
import ru.kamanin.nstu.graduate.thesis.shared.session.data.repository.LocalSessionRepositoryImpl
import ru.kamanin.nstu.graduate.thesis.shared.session.data.repository.RemoteSessionRepositoryImpl
import ru.kamanin.nstu.graduate.thesis.shared.session.domain.repository.LocalSessionRepository
import ru.kamanin.nstu.graduate.thesis.shared.session.domain.repository.RemoteSessionRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface SessionModule {

	companion object {

		@Provides
		@Singleton
		fun provideRemoteSessionRepository(
			@ioDispatcher ioDispatcher: CoroutineDispatcher,
			api: SessionApi
		): RemoteSessionRepository =
			RemoteSessionRepositoryImpl(ioDispatcher, api)
	}

	@Binds
	@Singleton
	fun bindLocalSessionRepository(impl: LocalSessionRepositoryImpl): LocalSessionRepository
}