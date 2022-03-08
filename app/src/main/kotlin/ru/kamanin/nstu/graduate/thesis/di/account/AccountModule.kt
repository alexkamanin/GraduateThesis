package ru.kamanin.nstu.graduate.thesis.di.account

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.kamanin.nstu.graduate.thesis.shared.account.data.repository.LocalAccountRepositoryImpl
import ru.kamanin.nstu.graduate.thesis.shared.account.data.repository.RemoteAccountRepositoryImpl
import ru.kamanin.nstu.graduate.thesis.shared.account.domain.repository.LocalAccountRepository
import ru.kamanin.nstu.graduate.thesis.shared.account.domain.repository.RemoteAccountRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface AccountModule {

	@Binds
	@Singleton
	fun bindRemoteAccountRepository(impl: RemoteAccountRepositoryImpl): RemoteAccountRepository

	@Binds
	@Singleton
	fun bindLocalAccountRepository(impl: LocalAccountRepositoryImpl): LocalAccountRepository
}