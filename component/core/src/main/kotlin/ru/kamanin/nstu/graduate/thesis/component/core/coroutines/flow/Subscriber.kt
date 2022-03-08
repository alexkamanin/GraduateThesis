package ru.kamanin.nstu.graduate.thesis.component.core.coroutines.flow

import androidx.lifecycle.LifecycleCoroutineScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

//For viewModel, later delete
fun <T> MutableStateFlow<T>.observe(observer: (T) -> Unit, coroutineScope: CoroutineScope) {
	onEach(observer).launchIn(coroutineScope)
}

inline fun <T> StateFlow<T>.subscribe(scope: LifecycleCoroutineScope, crossinline observer: () -> Unit) {
	onEach { observer() }.launchIn(scope)
}

inline fun <T> StateFlow<T>.subscribe(scope: LifecycleCoroutineScope, crossinline observer: (T) -> Unit) {
	onEach { observer(it) }.launchIn(scope)
}

inline fun <T> LiveState<T>.subscribe(scope: LifecycleCoroutineScope, crossinline observer: () -> Unit) {
	onEach { observer() }.launchIn(scope)
}

inline fun <T> LiveState<T>.subscribe(scope: LifecycleCoroutineScope, crossinline observer: (T) -> Unit) {
	onEach { observer(it) }.launchIn(scope)
}