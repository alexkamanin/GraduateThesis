package ru.kamanin.nstu.graduate.thesis.utils.coroutines.flow

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

//For viewModel, later delete
fun <T> MutableStateFlow<T>.observe(observer: (T) -> Unit, coroutineScope: CoroutineScope) {
	onEach(observer).launchIn(coroutineScope)
}

inline fun <T> StateFlow<T>.subscribe(owner: LifecycleOwner, crossinline observer: () -> Unit) {
	onEach { observer() }.launchIn(owner.lifecycleScope)
}

inline fun <T> StateFlow<T>.subscribe(owner: LifecycleOwner, crossinline observer: (T) -> Unit) {
	onEach { observer(it) }.launchIn(owner.lifecycleScope)
}

inline fun <T> LiveState<T>.subscribe(owner: LifecycleOwner, crossinline observer: () -> Unit) {
	onEach { observer() }.launchIn(owner.lifecycleScope)
}

inline fun <T> LiveState<T>.subscribe(owner: LifecycleOwner, crossinline observer: (T) -> Unit) {
	onEach { observer(it) }.launchIn(owner.lifecycleScope)
}