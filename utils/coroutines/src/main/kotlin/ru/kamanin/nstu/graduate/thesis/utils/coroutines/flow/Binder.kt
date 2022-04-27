package ru.kamanin.nstu.graduate.thesis.utils.coroutines.flow

import android.widget.EditText
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.*

fun MutableStateFlow<String>.bind(owner: LifecycleOwner, editText: EditText) {
	editText.textOfFlow().onEach(::emit).launchIn(owner.lifecycleScope)
}

private fun EditText.textOfFlow(): Flow<String> = callbackFlow {
	val textListener = doAfterTextChanged { trySend(it.toString()) }
	awaitClose { removeTextChangedListener(textListener) }
}