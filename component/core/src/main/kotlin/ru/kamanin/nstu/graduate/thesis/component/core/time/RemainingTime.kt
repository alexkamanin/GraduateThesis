package ru.kamanin.nstu.graduate.thesis.component.core.time

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onCompletion

typealias RemainingTime = Triple<Long, Long, Long>

fun getRemainingTime(endTime: Long, currentTime: Long): Flow<RemainingTime> =
	flow {
		var remainingTime = (endTime - currentTime) / 1000

		while (remainingTime >= 0) {

			val hours = remainingTime / 60 / 60
			val minutes = remainingTime / 60 % 60
			val seconds = remainingTime % 60

			emit(Triple(hours, minutes, seconds))
			delay(1000)

			remainingTime--
		}
	}.onCompletion {
		emit(Triple(0, 0, 0))
	}