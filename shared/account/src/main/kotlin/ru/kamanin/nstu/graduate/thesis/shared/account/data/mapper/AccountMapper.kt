package ru.kamanin.nstu.graduate.thesis.shared.account.data.mapper

import ru.kamanin.nstu.graduate.thesis.shared.account.data.dto.AccountDto
import ru.kamanin.nstu.graduate.thesis.shared.account.domain.entity.Account

fun AccountDto.toEntity() =
	Account(
		id = id,
		username = username,
		name = name,
		surname = surname,
		roles = roles
	)