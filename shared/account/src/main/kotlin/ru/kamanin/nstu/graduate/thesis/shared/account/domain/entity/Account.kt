package ru.kamanin.nstu.graduate.thesis.shared.account.domain.entity

import java.io.Serializable

data class Account(
	val id: Long,
	val username: String,
	val name: String,
	val surname: String,
	val roles: List<Roles>
) : Serializable