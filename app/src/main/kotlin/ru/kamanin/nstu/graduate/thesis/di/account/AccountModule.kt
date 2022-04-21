package ru.kamanin.nstu.graduate.thesis.di.account

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.kamanin.nstu.graduate.thesis.shared.account.data.repository.AccountMetaDataRepositoryImpl
import ru.kamanin.nstu.graduate.thesis.shared.account.data.repository.AccountRepositoryImpl
import ru.kamanin.nstu.graduate.thesis.shared.account.domain.repository.AccountMetaDataRepository
import ru.kamanin.nstu.graduate.thesis.shared.account.domain.repository.AccountRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface AccountModule {

	@Binds
	@Singleton
	fun bindAccountRepository(impl: AccountRepositoryImpl): AccountRepository

	@Binds
	@Singleton
	fun bindAccountMetaDataRepository(impl: AccountMetaDataRepositoryImpl): AccountMetaDataRepository
}