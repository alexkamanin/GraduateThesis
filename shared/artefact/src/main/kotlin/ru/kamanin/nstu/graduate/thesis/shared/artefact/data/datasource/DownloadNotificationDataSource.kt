package ru.kamanin.nstu.graduate.thesis.shared.artefact.data.datasource

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.core.app.NotificationCompat
import dagger.hilt.android.qualifiers.ApplicationContext
import ru.kamanin.nstu.graduate.thesis.shared.artefact.R
import ru.kamanin.nstu.graduate.thesis.shared.artefact.di.DownloadIconId
import ru.kamanin.nstu.graduate.thesis.shared.artefact.domain.entity.ArtefactMetaData
import javax.inject.Inject

interface DownloadNotificationDataSource {

	fun start(artefact: ArtefactMetaData)

	fun end(artefact: ArtefactMetaData, uri: Uri)

	fun cancel(artefact: ArtefactMetaData)
}

class DownloadNotificationDataSourceImpl @Inject constructor(
	@ApplicationContext private val context: Context,
	@DownloadIconId private val downloadIconId: Int
) : DownloadNotificationDataSource {

	private companion object {

		const val REQUEST_CODE = 0
	}

	private val channelId = context.getString(R.string.notification_download_channel_id)
	private val channelName = context.getString(R.string.notification_download_channel_name)
	private val downloadStartTitle = context.getString(R.string.notification_download_start_title)
	private val downloadEndTitle = context.getString(R.string.notification_download_end_title)

	private val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

	init {
		createNotificationChannel()
	}

	override fun start(artefact: ArtefactMetaData) {
		val notification = NotificationCompat.Builder(context, channelId)
			.setSmallIcon(downloadIconId)
			.setContentTitle(artefact.fullName)
			.setContentText(downloadStartTitle)
			.setAutoCancel(false)
			.setOngoing(true)
			.setCategory(NotificationCompat.CATEGORY_PROGRESS)
			.setProgress(0, 0, true)
			.build()

		notificationManager.notify(artefact.hashCode(), notification)
	}

	override fun end(artefact: ArtefactMetaData, uri: Uri) {
		val pendingIntent = getPendingIntent(artefact, uri)
		val notification = NotificationCompat.Builder(context, channelId)
			.setSmallIcon(downloadIconId)
			.setContentTitle(artefact.fullName)
			.setContentIntent(pendingIntent)
			.setContentText(downloadEndTitle)
			.setAutoCancel(true)
			.setOngoing(false)
			.setCategory(NotificationCompat.CATEGORY_PROGRESS)
			.build()

		notificationManager.notify(artefact.hashCode(), notification)
	}

	override fun cancel(artefact: ArtefactMetaData) {
		notificationManager.cancel(artefact.hashCode())
	}

	private fun getPendingIntent(artefact: ArtefactMetaData, uri: Uri): PendingIntent {
		val intent = Intent().apply {
			action = Intent.ACTION_VIEW
			addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
			addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
			setDataAndType(uri, artefact.mimeType)
		}
		val pendingIntent = PendingIntent.getActivity(
			context,
			REQUEST_CODE,
			intent,
			PendingIntent.FLAG_IMMUTABLE
		)

		return pendingIntent
	}

	private fun createNotificationChannel() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
			val channel = NotificationChannel(
				channelId,
				channelName,
				NotificationManager.IMPORTANCE_DEFAULT
			)
			notificationManager.createNotificationChannel(channel)
		}
	}
}