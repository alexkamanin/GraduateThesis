package ru.kamanin.nstu.graduate.thesis.di.firebase.notification

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.kamanin.graduate.thesis.shared.notification.data.repository.LocalFirebaseNotificationRepositoryImpl
import ru.kamanin.graduate.thesis.shared.notification.data.repository.RemoteFirebaseNotificationRepositoryImpl
import ru.kamanin.graduate.thesis.shared.notification.domain.repository.LocalFirebaseNotificationRepository
import ru.kamanin.graduate.thesis.shared.notification.domain.repository.RemoteFirebaseNotificationRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface FirebaseNotificationModule {

	@Binds
	@Singleton
	fun bindLocalFirebaseRepository(impl: LocalFirebaseNotificationRepositoryImpl): LocalFirebaseNotificationRepository

	@Binds
	@Singleton
	fun bindRemoteFirebaseRepository(impl: RemoteFirebaseNotificationRepositoryImpl): RemoteFirebaseNotificationRepository
}