package ru.kamanin.nstu.graduate.thesis.shared.account.domain.repository

interface RemoteAccountRepository {

	suspend fun changePassword(username: String, password: String, verificationCode: String)
}