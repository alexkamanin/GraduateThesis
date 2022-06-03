package ru.kamanin.graduate.thesis.shared.notification.domain.repository

interface LocalFirebaseNotificationRepository {

	fun setToken(token: String)

	fun getToken(): String?
}