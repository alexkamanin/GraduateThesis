package ru.kamanin.nstu.graduate.thesis.di.firebase.notification

import android.content.Context
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ru.kamanin.graduate.thesis.shared.notification.data.repository.LocalFirebaseNotificationRepositoryImpl
import ru.kamanin.graduate.thesis.shared.notification.data.repository.RemoteFirebaseNotificationRepositoryImpl
import ru.kamanin.graduate.thesis.shared.notification.di.*
import ru.kamanin.graduate.thesis.shared.notification.domain.entity.AnswerNotification
import ru.kamanin.graduate.thesis.shared.notification.domain.entity.ExamNotification
import ru.kamanin.graduate.thesis.shared.notification.domain.entity.MessageNotification
import ru.kamanin.graduate.thesis.shared.notification.domain.repository.LocalFirebaseNotificationRepository
import ru.kamanin.graduate.thesis.shared.notification.domain.repository.RemoteFirebaseNotificationRepository
import ru.kamanin.nstu.graduate.thesis.R
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface FirebaseNotificationModule {

	companion object {

		@Provides
		@Singleton
		@ExamChannelId
		fun provideExamChannelId(@ApplicationContext context: Context) =
			context.getString(R.string.exam_notification_channel_id)

		@Provides
		@Singleton
		@ExamChannelName
		fun provideExamChannelName(@ApplicationContext context: Context) =
			context.getString(R.string.exam_notification_channel_name)

		@Provides
		@Singleton
		@AnswerChannelId
		fun provideTaskChannelId(@ApplicationContext context: Context) =
			context.getString(R.string.task_notification_channel_id)

		@Provides
		@Singleton
		@AnswerChannelName
		fun provideTaskChannelName(@ApplicationContext context: Context) =
			context.getString(R.string.task_notification_channel_name)

		@Provides
		@Singleton
		@MessageChannelId
		fun provideMessageChannelId(@ApplicationContext context: Context) =
			context.getString(R.string.message_notification_channel_id)

		@Provides
		@Singleton
		@MessageChannelName
		fun provideMessageChannelName(@ApplicationContext context: Context) =
			context.getString(R.string.message_notification_channel_name)

		@Provides
		@Singleton
		fun provideMessageAdapter(): JsonAdapter<MessageNotification> =
			Moshi.Builder().build().adapter(MessageNotification::class.java)

		@Provides
		@Singleton
		fun provideExamAdapter(): JsonAdapter<ExamNotification> =
			Moshi.Builder().build().adapter(ExamNotification::class.java)

		@Provides
		@Singleton
		fun provideTaskAdapter(): JsonAdapter<AnswerNotification> =
			Moshi.Builder().build().adapter(AnswerNotification::class.java)
	}

	@Binds
	@Singleton
	fun bindLocalFirebaseRepository(impl: LocalFirebaseNotificationRepositoryImpl): LocalFirebaseNotificationRepository

	@Binds
	@Singleton
	fun bindRemoteFirebaseRepository(impl: RemoteFirebaseNotificationRepositoryImpl): RemoteFirebaseNotificationRepository
}