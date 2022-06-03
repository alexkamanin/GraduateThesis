package ru.kamanin.graduate.thesis.shared.notification.domain.repository

interface RemoteFirebaseNotificationRepository {

	suspend fun sendToken(token: String)
}