package ru.kamanin.nstu.graduate.thesis.shared.account.domain.repository

interface AccountRepository {

	suspend fun changePassword(username: String, password: String, verificationCode: String)
}