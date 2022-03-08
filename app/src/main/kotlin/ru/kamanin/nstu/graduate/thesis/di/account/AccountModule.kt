package ru.kamanin.nstu.graduate.thesis.di.account

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import ru.kamanin.nstu.graduate.thesis.component.core.coroutines.dispatcher.ioDispatcher
import ru.kamanin.nstu.graduate.thesis.shared.account.data.api.AccountApi
import ru.kamanin.nstu.graduate.thesis.shared.account.data.repository.AccountRepositoryImpl
import ru.kamanin.nstu.graduate.thesis.shared.account.domain.repository.AccountRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AccountModule {

	@Provides
	@Singleton
	fun provideAccountRepository(
		@ioDispatcher ioDispatcher: CoroutineDispatcher,
		api: AccountApi
	): AccountRepository =
		AccountRepositoryImpl(ioDispatcher, api)
}