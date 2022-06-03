package ru.kamanin.nstu.graduate.thesis.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import dagger.hilt.android.AndroidEntryPoint
import ru.kamanin.graduate.thesis.shared.notification.domain.repository.LocalFirebaseNotificationRepository
import ru.kamanin.nstu.graduate.thesis.R
import ru.kamanin.nstu.graduate.thesis.component.ui.resources.R.drawable.*
import javax.inject.Inject

@AndroidEntryPoint
class FirebaseNotificationService : FirebaseMessagingService() {

	@Inject
	lateinit var repository: LocalFirebaseNotificationRepository

	private lateinit var notificationManager: NotificationManager
	private lateinit var channelId: String
	private lateinit var channelName: String

	private companion object Keys {

		const val TOKEN_EVENT = "com.google.firebase.messaging.NEW_TOKEN"
		const val NEW_TOKEN = "token"

		const val NOTIFICATION_ID = "google.message_id"
		const val NOTIFICATION_TITLE = "gcm.notification.title"
		const val NOTIFICATION_BODY = "gcm.notification.body"
	}

	override fun onCreate() {
		super.onCreate()
		initNotifications()
	}

	private fun initNotifications() {
		notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
		channelId = getString(R.string.default_notification_channel_id)
		channelName = getString(R.string.default_notification_channel_name)
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
			val channel = NotificationChannel(
				channelId,
				channelName,
				NotificationManager.IMPORTANCE_HIGH
			)
			notificationManager.createNotificationChannel(channel)
		}
	}

	override fun onNewToken(token: String) {
		repository.setToken(token)
	}

	override fun handleIntent(intent: Intent) {

		if (intent.isTokenEvent()) {
			val token = requireNotNull(intent.getStringExtra(NEW_TOKEN))
			onNewToken(token)
			return
		}

		val messageId = intent.extras?.getString(NOTIFICATION_ID)
		val messageTitle = intent.extras?.getString(NOTIFICATION_TITLE)
		val messageBody = intent.extras?.getString(NOTIFICATION_BODY)

		sendNotification(messageId.hashCode(), messageTitle, messageBody)
	}

	private fun Intent.isTokenEvent(): Boolean =
		TOKEN_EVENT == action

	private fun sendNotification(
		id: Int,
		title: String?,
		text: String?,
	) {
		val notificationBuilder = NotificationCompat.Builder(this, channelId)
			.setSmallIcon(ic_notification)
			.setContentTitle(title)
			.setStyle(NotificationCompat.BigTextStyle().bigText(text))
			.setContentText(text)
			.setAutoCancel(true)
			.setCategory(NotificationCompat.CATEGORY_MESSAGE)
			.setDefaults(NotificationCompat.DEFAULT_ALL)
			.setPriority(NotificationCompat.PRIORITY_HIGH)

		notificationManager.notify(id, notificationBuilder.build())

	}
}