package ru.kamanin.nstu.graduate.thesis.utils.coroutines.flow

import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow

fun <T> MutableLiveState() =
	MutableSharedFlow<T>(
		replay = 0,
		onBufferOverflow = BufferOverflow.DROP_OLDEST,
		extraBufferCapacity = 1
	)

typealias LiveState<T> = SharedFlow<T>

fun <T> MutableSharedFlow<T>.asLiveState() = asSharedFlow()

operator fun <T> MutableSharedFlow<T>.invoke(value: T) {
	tryEmit(value)
}