package ru.kamanin.nstu.graduate.thesis.component.core.coroutines.flow

import android.widget.EditText
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.LifecycleCoroutineScope
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.*

fun MutableStateFlow<String>.bind(coroutineScope: LifecycleCoroutineScope, editText: EditText) {
	editText.textOfFlow().onEach { this.emit(it) }.launchIn(coroutineScope)
}

private fun EditText.textOfFlow(): Flow<String> = callbackFlow {
	val textListener = doAfterTextChanged { trySend(it.toString()) }
	awaitClose { removeTextChangedListener(textListener) }
}