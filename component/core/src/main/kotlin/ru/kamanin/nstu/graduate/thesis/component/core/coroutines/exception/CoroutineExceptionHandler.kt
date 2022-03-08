package ru.kamanin.nstu.graduate.thesis.component.core.coroutines.exception

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.launch

inline fun CoroutineScope.launch(
	crossinline error: (Throwable) -> Unit,
	noinline block: suspend CoroutineScope.() -> Unit
) {
	val handler = CoroutineExceptionHandler { _, throwable -> error(throwable) }
	launch(handler, CoroutineStart.DEFAULT, block)
}