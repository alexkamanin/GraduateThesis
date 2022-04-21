package ru.kamanin.nstu.graduate.thesis.shared.account.data.dto

import ru.kamanin.nstu.graduate.thesis.shared.account.domain.entity.Roles

data class AccountDto(
	val id: Long,
	val username: String,
	val name: String,
	val surname: String,
	val roles: List<Roles>
)